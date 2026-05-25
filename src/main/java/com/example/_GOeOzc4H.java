//package com.example;
//
//import com.example.client.CharacterService;
//import com.example.client.SoapCharacterService;
//
//public class _GOeOzc4H {
//
//    public static void main(String[] args){
//        String studentCode = "B22DCCN692";
//        String qCode = "GOeOzc4H";
//
//        CharacterService characterService = new CharacterService();
//        SoapCharacterService port = characterService.getSoapCharacterServicePort();
//
//        String data = port.requestString(studentCode, qCode);
//        String answer = "";
//        for(int i = data.length() - 1; i >= 0; i--){
//            answer += data.charAt(i);
//        }
//        port.submitString(studentCode, qCode, answer);
//    }
//}
