export class CreateSessionModel {
    categoryId: number = 0;
    topicId: number = null;
    minNumberOfCardsPerParticipant:number = null;
    maxNumberOfCardsPerParticipant:number = null;
    participantsCanAddCards:boolean = false;
    cardCommentsAllowed:boolean = false;
    amountOfCircles:number = null;
    type: string = "sync";


    constructor(minNumberOfCardsPerParticipant:number,maxNumberOfCardsPerParticipant:number,participantsCanAddCards:boolean,cardCommentsAllowed:boolean,amountOfCircles:number, categoryId:number, topicId:number) {
        this.minNumberOfCardsPerParticipant = minNumberOfCardsPerParticipant;
        this.maxNumberOfCardsPerParticipant = maxNumberOfCardsPerParticipant;
        this.participantsCanAddCards = participantsCanAddCards;
        this.cardCommentsAllowed = cardCommentsAllowed;
        this.amountOfCircles = amountOfCircles;
        this.categoryId = categoryId;
        this.topicId = topicId;
    }

    public static createEmptyCreateSession():CreateSessionModel {
        return new CreateSessionModel(null,null,false,false,null,0,null);
    }
}