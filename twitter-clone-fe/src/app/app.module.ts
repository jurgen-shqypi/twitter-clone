import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/auth/signup/signup.component';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { MainComponent } from './components/main/main.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { BannerComponent } from './components/banner/banner.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HomeComponent } from './components/home/home.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import {TextFieldModule} from '@angular/cdk/text-field';
import { TokenInterceptor } from './components/auth/token-interceptor';
import { ComposeTweetComponent } from './components/compose-tweet/compose-tweet.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { AuthGuard } from './components/auth/auth.guard';
import { LoginGuard } from './components/auth/login.guard';
import { PostComponent } from './components/post/post.component';
import { BookmarkComponent } from './components/bookmark/bookmark.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import { UserLikedListComponent } from './components/user-liked-list/user-liked-list.component';
import {MatDialogModule} from '@angular/material/dialog';
import { SettingsComponent } from './components/settings/settings.component';
import { ChangeUsernameComponent } from './components/settings/change-username/change-username.component';
import { ChangePasswordComponent } from './components/settings/change-password/change-password.component';
import { HotToastModule } from '@ngneat/hot-toast';

const routes: Routes = [
  {path: 'bookmarks', component: LandingPageComponent, canActivate: [AuthGuard]},
  {path: 'settings', component: LandingPageComponent, canActivate: [AuthGuard]},
  {path: 'home', component: LandingPageComponent, canActivate: [AuthGuard]},
  {path: 'post/:postId', component: MainComponent, canActivate: [AuthGuard]},
  {path: 'user/:username', component: MainComponent, canActivate: [AuthGuard]},
  {path: 'edit/profile', component: MainComponent, canActivate: [AuthGuard]},
  {path: 'login', component: AuthComponent, canActivate: [LoginGuard]},
  {path: 'signup', component: AuthComponent, canActivate: [LoginGuard]},
  {path: '', component: MainComponent, canActivate: [LoginGuard]},
  // {path: '**', redirectTo: '', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    LoginComponent,
    SignupComponent,
    MainComponent,
    UserProfileComponent,
    BannerComponent,
    SidebarComponent,
    HomeComponent,
    LandingPageComponent,
    ComposeTweetComponent,
    EditProfileComponent,
    PostComponent,
    BookmarkComponent,
    PostDetailsComponent,
    UserLikedListComponent,
    SettingsComponent,
    ChangeUsernameComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    HttpClientModule, 
    TextFieldModule, BrowserAnimationsModule,
    MatProgressSpinnerModule, MatDialogModule,
    HotToastModule.forRoot({
      position: 'bottom-center',
      theme: 'snackbar'
    })
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  },AuthGuard, LoginGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
