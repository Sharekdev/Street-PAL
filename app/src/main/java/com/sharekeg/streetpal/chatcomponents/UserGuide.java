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
            GOING_TO_POLICE_STATION = 7,
            PEOPLE_NOT_HELPING = 8,
            PEOPLE_HELPING_NOT_GOING_TO_POLICE = 9,
            SEND_STRESS_SIGNAL = 1000,
            USER_IS_SAFE = 1001,
            TERMINATE_CHAT = -1;
    public static final String CALL_TRUSTED_CONTACT = "call_trusted_contact",
            SAFE_PLACE = "Safe place", POLICE_STATION = "police", HOSPITAL = "hospital", HEADING_TO_POLICE = "going_to_police", HEADING_TO_HOSPITAL = "going_to_hospital";
    private OnUserStatusChangeListener listener;
    private ChatBlock chatBlock;

    public UserGuide(OnUserStatusChangeListener listener) {
        this.listener = listener;
    }


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
            case USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE:
                ArrayList<ChatMessage> chatMessages1 = new ArrayList<>();
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part1).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part2).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part3).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_part4).toString(), false));
                chatMessages1.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions1 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages1, userOptions1);
                break;
            case USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE:
                ArrayList<ChatMessage> chatMessages4 = new ArrayList<>();
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part1).toString(), false));
//                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part2).toString(), "safe_place", false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part3).toString(), false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_sorroundings_not_safe_part4).toString(), CALL_TRUSTED_CONTACT, false));
                chatMessages4.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions4 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages4, userOptions4);
                break;
            case DOESNT_KNOW_IF_SORROUNDINGS_ARE_SAFE:
                ArrayList<ChatMessage> chatMessages5 = new ArrayList<>();
                chatMessages5.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_doesnt_know_sorroundings_safe_part1).toString(), false));
                chatMessages5.add(new ChatMessage(context.getResources().getText(R.string.user_guide_user_doesnt_know_sorroundings_safe_part2).toString(), false));
                chatMessages5.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_qustion1).toString(), false));
                UserOptions userOptions5 = new UserOptions(context.getResources().getText(R.string.user_guide_postive_button).toString(), context.getResources().getText(R.string.user_guide_negative_button).toString(), context.getResources().getText(R.string.user_guide_agnostic_button).toString(), USER_IS_FOLLOWED_SURROUNDINGS_ARE_SAFE, USER_IS_FOLLOWED_SURROUNDINGS_ARE_NOT_SAFE, DOESNT_KNOW_IF_SORROUNDINGS_ARE_SAFE);
                chatBlock = new ChatBlock(chatMessages5, userOptions5);
                break;
            case USER_FEELS_IN_DANGER:
                ArrayList<ChatMessage> chatMessages6 = new ArrayList<>();
                chatMessages6.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_part1).toString(), false));
                chatMessages6.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_part2).toString(), false));
                chatMessages6.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_part3).toString(), false));
                chatMessages6.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_part4).toString(), false));
                chatMessages6.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_part5).toString(), false));
                UserOptions userOptions6 = new UserOptions(context.getResources().getText(R.string.user_in_danger_first_question_answer_1).toString(), context.getResources().getText(R.string.user_in_danger_first_question_answer_2).toString(), context.getResources().getText(R.string.user_in_danger_first_question_answer_3).toString(), PEOPLE_NOT_HELPING, PEOPLE_HELPING_NOT_GOING_TO_POLICE, GOING_TO_POLICE_STATION);
                chatBlock = new ChatBlock(chatMessages6, userOptions6);
                listener.OnUserStatusChange(USER_FEELS_IN_DANGER);
                break;

            case PEOPLE_NOT_HELPING:
                ArrayList<ChatMessage> chatMessages7 = new ArrayList<>();
                chatMessages7.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_no_help_part1).toString(), false));
                chatMessages7.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_no_help_part2).toString(), HOSPITAL, false));
                chatMessages7.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_no_help_part3).toString(), POLICE_STATION, false));
                chatMessages7.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions7 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages7, userOptions7);
                break;
            case PEOPLE_HELPING_NOT_GOING_TO_POLICE:
                ArrayList<ChatMessage> chatMessages8 = new ArrayList<>();
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_no_help_part1).toString(), false));
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_help_not_police_part1).toString(), false));
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_help_not_police_part2).toString(), HEADING_TO_POLICE, false));
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_help_not_police_part3).toString(), false));
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_help_not_police_part4).toString(), HOSPITAL, false));
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_no_help_part3).toString(), POLICE_STATION, false));
                chatMessages8.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions8 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages8, userOptions8);
                break;
            case GOING_TO_POLICE_STATION:
                ArrayList<ChatMessage> chatMessages9 = new ArrayList<>();
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_no_help_part1).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_going_police_part1).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.user_in_danger_going_police_part2).toString(), POLICE_STATION, false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_1).toString(), POLICE_STATION, false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_2).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_3).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_4).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_5).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_6).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_7).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_8).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_9).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_10).toString(), false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.police_station_package_11).toString(), HOSPITAL, false));
                chatMessages9.add(new ChatMessage(context.getResources().getText(R.string.user_guide_case_1_question2).toString(), false));
                UserOptions userOptions9 = new UserOptions(context.getResources().getText(R.string.user_guide_cas1_answer2_positive_button).toString(), context.getResources().getText(R.string.user_guide_cas1_answer2_negative_button).toString(), USER_IS_SAFE, SEND_STRESS_SIGNAL);
                chatBlock = new ChatBlock(chatMessages9, userOptions9);
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
                listener.OnUserStatusChange(SEND_STRESS_SIGNAL);
                break;
            case USER_HAS_BEEN_HARASSED:
                //go to guide for now

                break;

        }

        return chatBlock;
    }
}
