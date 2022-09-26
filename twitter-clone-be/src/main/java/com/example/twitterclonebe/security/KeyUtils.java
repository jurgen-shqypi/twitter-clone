package com.example.twitterclonebe.security;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Objects;

@Component
public class KeyUtils {

    @Autowired
    Environment environment;

    @Value("${access-token.private.key}")
    private String accessTokenPrivateKeyPath;

    @Value("${access-token.public.key}")
    private String accessTokenPublicKeyPath;

    @Value("${refresh-token.private.key}")
    private String refreshTokenPrivateKeyPath;

    @Value("${refresh-token.public.key}")
    private String refreshTokenPublicKeyPath;

    private KeyPair _accessTokenKeyPair;
    private KeyPair _refreshTokenKeyPair;

    private KeyPair getAccessTokenKeyPair(){
        if (Objects.isNull(_accessTokenKeyPair)){
            _accessTokenKeyPair = getKeyPair(accessTokenPrivateKeyPath, accessTokenPublicKeyPath);
        }
        return _accessTokenKeyPair;
    }

    private KeyPair getRefreshTokenKeyPair() {
        if (Objects.isNull(_refreshTokenKeyPair)) {
            _refreshTokenKeyPair = getKeyPair(refreshTokenPrivateKeyPath, refreshTokenPublicKeyPath);
        }
        return _refreshTokenKeyPair;
    }

    private KeyPair getKeyPair(String privateKeyPath, String publicKeyPath){
            KeyPair keyPair;
            try{
                ClassPathResource classPathResource = new ClassPathResource(privateKeyPath);
                InputStream privateKeyInputStream;
                privateKeyInputStream = classPathResource.getInputStream();
                File privateKeyFile = File.createTempFile("private-key", ".key");
                FileUtils.copyInputStreamToFile(privateKeyInputStream, privateKeyFile);

                InputStream publicKeyInputStream;
                classPathResource = new ClassPathResource(publicKeyPath);
                publicKeyInputStream = classPathResource.getInputStream();
                File publicKeyFile = File.createTempFile("public-key", ".key");
                FileUtils.copyInputStreamToFile(publicKeyInputStream, publicKeyFile);

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                System.out.println(publicKeyBytes.length);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

                keyPair = new KeyPair(publicKey, privateKey);
                return keyPair;
            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
                if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
                    throw new RuntimeException(e);
                } else {
                    try {
                        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                        keyPairGenerator.initialize(2048);
                        keyPair = keyPairGenerator.generateKeyPair();
                        try (OutputStream fos = new FileOutputStream(publicKeyPath)) {
                            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
                            fos.write(keySpec.getEncoded());
                        }

                        try (OutputStream fos = new FileOutputStream(privateKeyPath)) {
                            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
                            fos.write(keySpec.getEncoded());
                        }
                    } catch (NoSuchAlgorithmException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

        }
        return keyPair;
    }

    public RSAPublicKey getAccessTokenPublicKey() {
        return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
    };
    public RSAPrivateKey getAccessTokenPrivateKey() {
        return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
    };
    public RSAPublicKey getRefreshTokenPublicKey() {
        return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
    };
    public RSAPrivateKey getRefreshTokenPrivateKey() {
        return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
    };

}
