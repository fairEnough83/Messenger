import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MessangerSharedModule } from 'app/shared';
import {
    ConversationComponent,
    ConversationDetailComponent,
    ConversationUpdateComponent,
    ConversationDeletePopupComponent,
    ConversationDeleteDialogComponent,
    conversationRoute,
    conversationPopupRoute
} from './';

const ENTITY_STATES = [...conversationRoute, ...conversationPopupRoute];

@NgModule({
    imports: [MessangerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ConversationComponent,
        ConversationDetailComponent,
        ConversationUpdateComponent,
        ConversationDeleteDialogComponent,
        ConversationDeletePopupComponent
    ],
    entryComponents: [
        ConversationComponent,
        ConversationUpdateComponent,
        ConversationDeleteDialogComponent,
        ConversationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MessangerConversationModule {}
