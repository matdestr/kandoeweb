import {Component, OnInit} from "angular2/core";
import {Router, RouteParams} from "angular2/router";

import {Topic} from "../../entities/topic/topic";
import {Category} from "../../entities/category/category";
import {CardDetails} from "../../entities/category/card-details";
import {User} from "../../entities/user/user";

import {TopicService} from "../../services/topic.service";
import {CategoryService} from "../../services/category.service";
import {CardDetailsService} from "../../services/card-details.service";

import {ToolbarComponent} from "../widget/toolbar.component";
import {CardDetailComponent} from "../cards/card-detail.component";

import {error} from "util";

@Component({
    selector: 'topic-detail',
    templateUrl: 'html/topic/topic-detail.html',
    directives: [ToolbarComponent, CardDetailComponent]
})
export class TopicDetailComponent implements OnInit {
    private topic:Topic = Topic.createEmptyTopic();
    private cards:CardDetails[] = [];
    private currentCard:CardDetails;
    private categoryCards:CardDetails[] = [];
    private categoryCardsToAdd:CardDetails[] = [];

    constructor(private _router:Router,
                private _routeArgs:RouteParams,
                private _topicService:TopicService,
                private _cardDetailsService:CardDetailsService) {
    }


    ngOnInit():any {
        var topicId:number = +this._routeArgs.params["topicId"];

        this._topicService.getTopic(topicId).subscribe(
            data => {
                this.topic = this.topic.deserialize(data.json());
            }
        );

        this._cardDetailsService.getCardDetailsOfTopic(topicId).subscribe(
            data => {
                for (let card of data.json())
                    this.cards.push(CardDetails.createEmptyCard().deserialize(card));
            }
        );
    }

    public openCardChooser():void {
        this._router.navigate(["/TopicCardChooser", {topicId: this.topic.topicId}]);
    }

    public onCardClick(card:CardDetails):void {
        this.currentCard = card;
    }

    public toAddNewSession(categoryId:number, topicId:number) {
        this._router.navigate(["/CreateSession", {categoryId: categoryId, topicId: topicId}])
    }
}