import { PostResponse } from "./post-response";
import { User } from "./user";

export class PostDetailed {
    postId: string = '';
    description: string = '';
    photoLink: string = '';
    userDTO: User = new User;
    liked: boolean = false;
    saved: boolean = false;
    numOfLikes: number = 0;
    numOfComments: number = 0;
    comments: PostResponse[] = []
}
