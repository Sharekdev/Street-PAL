package com.sharekeg.streetpal.chatcomponents;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sharekeg.streetpal.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by Khalid on 7/31/2017.
 */
public class UserGuide {
    public static final int USER_CALLS_HELP = 0,
            USER_FEELS_FOLLOWED = 1,
            USER_FEELS_IN_DANGER = 2,
            USER_HAS_BEEN_HARASSED = 3,
            USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE = 4,
            USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE = 5,
            DOESNT_KNOW_IF_SORROUNDINGS_ARE_SAFE = 6,
            SEND_STRESS_SIGNAL = 1000,
            USER_IS_SAFE = 1001,
            TERMINATE_CHAT = -1;


    private ChatBlock chatBlock;


    public ChatBlock guideUserToSafety(int situationId, Context context) {

        switch (situationId) {
            case USER_CALLS_HELP:

//                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_question_1).toString(), context.getResources().getText(R.string.user_guide_case_1).toString()
//                        , context.getResources().getText(R.string.user_guide_case_2).toString(),context.getResources().getText(R.string.user_guide_neutral_button).toString(), USER_FEELS_FOLLOWED, SEND_STRESS_SIGNAL, KNOW_MORE);
                break;
            case USER_FEELS_FOLLOWED:
                ArrayList<ChatMessage> chatMessages = new ArrayList<>();
                UserOptions userOptions = new UserOptions(context.getResources().getText(R.string.user_guide_postive_button).toString(), context.getResources().getText(R.string.user_guide_negative_button).toString(), context.getResources().getText(R.string.user_guide_agnostic_button).toString(), USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE, USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE, DOESNT_KNOW_IF_SORROUNDINGS_ARE_SAFE);
                chatMessages.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_qustion1).toString(), false));
                chatBlock = new ChatBlock(chatMessages, userOptions);
                break;
            case USER_FEELS_IN_DANGER:
                //will be implemented soon
                break;
            case USER_HAS_BEEN_HARASSED:
                //go to gide for now

                break;
            case DOESNT_KNOW_IF_SORROUNDINGS_ARE_SAFE:
                ArrayList<ChatMessage> chatMessages5 = new ArrayList<>();
                chatMessages5.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_doesnt_know_sorroundings_safe_part1).toString(), false));
                chatMessages5.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_doesnt_know_sorroundings_safe_part2).toString(), false));
                chatMessages5.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_qustion1).toString(), false));
                UserOptions userOptions5 = new UserOptions(context.getResources().getText(R.string.user_guide_postive_button).toString(), context.getResources().getText(R.string.user_guide_negative_button).toString(), context.getResources().getText(R.string.user_guide_agnostic_button).toString(), USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE, USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE, DOESNT_KNOW_IF_SORROUNDINGS_ARE_SAFE);
                chatBlock = new ChatBlock(chatMessages5, userOptions5);
                break;
            case USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE:
                ArrayList<ChatMessage> chatMessages1 = new ArrayList<>();
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part1).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part2).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part3).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part4).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions1 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages1, userOptions1);
//                chatMessage = new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_qustion2).toString(), context.getResources().getText(R.string.user_guide_postive_button).toString(), context.getResources().getText(R.string.user_guide_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                break;
            case USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE:
                ArrayList<ChatMessage> chatMessages4 = new ArrayList<>();
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part1).toString(), false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part2).toString(), "safe_place", false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part3).toString(), false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part4).toString(), "call_trusted_contact", false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions4 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages4, userOptions4);
                break;
            case USER_IS_SAFE:
                ArrayList<ChatMessage> chatMessages2 = new ArrayList<>();
                chatMessages2.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_safe).toString(), false));
                UserOptions userOptions2 = new UserOptions(context.getResources().getText(R.string.user_guide_terminate_chat).toString(), TERMINATE_CHAT);
                chatBlock = new ChatBlock(chatMessages2, userOptions2);
                break;
            case SEND_STRESS_SIGNAL:
                ArrayList<ChatMessage> chatMessages3 = new ArrayList<>();
                chatMessages3.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_not_safe_part1).toString(), false));
                chatMessages3.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_not_safe_part2).toString(), false));
                UserOptions userOptions3 = new UserOptions(context.getResources().getText(R.string.user_guide_case_1).toString(), context.getResources().getText(R.string.user_guide_case_2).toString(), context.getResources().getText(R.string.user_guide_neutral_button).toString(), USER_FEELS_FOLLOWED, USER_FEELS_IN_DANGER, USER_HAS_BEEN_HARASSED);
                chatBlock = new ChatBlock(chatMessages3, userOptions3);
                break;

        }

        return chatBlock;
    }
}
