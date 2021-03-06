System.register(['angular2/core', "angular2/router"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var __param = (this && this.__param) || function (paramIndex, decorator) {
        return function (target, key) { decorator(target, key, paramIndex); }
    };
    var core_1, router_1;
    var SignOutComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            }],
        execute: function() {
            /**
             * This component is responsible for all the functionality of sign out
             */
            SignOutComponent = (function () {
                function SignOutComponent(_router, _tokenName) {
                    this._router = _router;
                    this._tokenName = _tokenName;
                }
                SignOutComponent.prototype.onSignOut = function () {
                    localStorage.removeItem(this._tokenName);
                    localStorage.removeItem(this._tokenName + '-expire-date');
                    localStorage.removeItem(this._tokenName + '-refresh-token');
                    this._router.navigate(['Authentication']);
                };
                __decorate([
                    core_1.Input(), 
                    __metadata('design:type', Object)
                ], SignOutComponent.prototype, "anchorSignOutClassName", void 0);
                SignOutComponent = __decorate([
                    core_1.Component({
                        selector: 'sign-out',
                        template: '<a [className]="anchorSignOutClassName" (click)="onSignOut()">Sign out</a>'
                    }),
                    __param(1, core_1.Inject('App.TokenName')), 
                    __metadata('design:paramtypes', [router_1.Router, String])
                ], SignOutComponent);
                return SignOutComponent;
            })();
            exports_1("SignOutComponent", SignOutComponent);
        }
    }
});
//# sourceMappingURL=sign-out.component.js.map