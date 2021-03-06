import {Serializable} from "../../util/serializable";

/**
 * This class represents a token used to authenticate the user.
 * */
export class Token {
    access_token : string;
    expires_in : number;
    token_type : string;
    refresh_token : string;
    scope : string;
    jti : string;
}
