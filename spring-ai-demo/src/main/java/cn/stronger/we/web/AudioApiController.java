//package cn.stronger.we.web;
//
//
//import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
//import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
//import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
//import org.springframework.ai.openai.api.OpenAiAudioApi;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author qiang.w
// * @version release-1.0.0
// * @class AudioApiController.class
// * @department Platform R&D
// * @date 2025/2/25
// * @desc do what?
// */
//@RestController
//public class AudioApiController {
//
//    @Autowired
//    private OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
//    @Value("classpath:/speech/jfk.flac")
//    private Resource audioFile;
//
//    /**
//     * 语音转文字
//     */
//    @GetMapping("/ai/audioTranscription")
//    private String audioTranscription(){
//        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
//                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.VTT)
//                .model("FunAudioLLM/SenseVoiceSmall")
//                .temperature(0f)
//                .build();
//        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
//        AudioTranscriptionResponse response = openAiAudioTranscriptionModel.call(transcriptionRequest);
//        return response.getResult().getOutput();
//    }
//}
