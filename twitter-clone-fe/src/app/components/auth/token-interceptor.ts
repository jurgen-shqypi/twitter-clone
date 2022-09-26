import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from "rxjs";
import { TokenDTO } from "src/app/common/token-dto";
import { AuthService } from "src/app/services/auth.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor{

    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

    constructor(private authService: AuthService){}
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(this.authService.getAccessToken() === null || this.authService.getAccessToken() === "" || req.url.includes("token")){
            return next.handle(req);
        }
        req = this.addToken(req, this.authService.getAccessToken()!);
        return next.handle(req).pipe(catchError(error=> {
            if(error instanceof HttpErrorResponse && error.status  === 401) {
                return this.handle401Error(req, next);
            } else {
                return throwError(error);
            }
        }));
    }
    handle401Error(request: HttpRequest<any>, next: HttpHandler) {
        if(this.isRefreshing === false) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);
            return this.authService.refreshToken().pipe(
                switchMap((tokenDTO: TokenDTO) => {
                    this.isRefreshing = false;
                    this.refreshTokenSubject.next(tokenDTO.accessToken);
                    return next.handle(this.addToken(request, tokenDTO.accessToken));
                })
            )
        } else {
            return this.refreshTokenSubject.pipe(
              filter(token => token != null),
              take(1),
              switchMap(jwt => {
                return next.handle(this.addToken(request, jwt));
              }));
            }
    }

    private addToken(request: HttpRequest<any>, token: string) {
        return request.clone({
          setHeaders: {
            'Authorization': `Bearer ${token}`
          }
        });
    }

    // addToken(req: HttpRequest<any>, jwtToken: any) {
    //     return req.clone({
    //         headers: req.headers.set('Authorization',
    //             'Bearer ' + jwtToken)
    //     });
    // }
}
