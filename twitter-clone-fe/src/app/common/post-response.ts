import { User } from "./user";

export class PostResponse {
    postId: string = '';
    description: string = '';
    photoLink: string = '';
    userDTO: User = new User;
    liked: boolean = false;
    saved: boolean = false;
    numOfLikes: number = 0;
    numOfComments: number = 0;
}
