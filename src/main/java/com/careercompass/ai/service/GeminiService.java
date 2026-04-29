package com.careercompass.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateContent(String prompt) {
        if (apiKey == null || apiKey.trim().isEmpty() || apiKey.equals("AIzaSyCz8rUY1oGNRcA-hqQXTeUafziaupeGdVc")) {
            System.err.println("Gemini API Key is missing or default invalid key detected! Using fallback.");
            return generateMockResponse(prompt);
        }

        // Using v1 for better stability, or v1beta for flash-1.5 specific features
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey.trim();

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> contents = new HashMap<>();
        Map<String, Object> parts = new HashMap<>();
        
        parts.put("text", prompt);
        contents.put("parts", List.of(parts));
        requestBody.put("contents", List.of(contents));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.postForEntity(url, entity, JsonNode.class);
            JsonNode body = response.getBody();
            
            if (body != null && body.has("candidates") && body.get("candidates").isArray() && body.get("candidates").size() > 0) {
                JsonNode candidate = body.get("candidates").get(0);
                if (candidate.has("content") && candidate.get("content").has("parts")) {
                    return candidate.get("content").get("parts").get(0).get("text").asText();
                }
            }
            
            System.err.println("Gemini API returned unexpected structure or no candidates: " + body);
            return null;
        } catch (Exception e) {
            System.err.println("Gemini API Request Failed: " + e.getMessage());
            e.printStackTrace();
            // Fallback for demonstration when API key is missing/invalid
            return generateMockResponse(prompt);
        }
    }

    private String generateMockResponse(String prompt) {
        String lower = prompt.toLowerCase();
        if (lower.contains("hint")) {
            return "Consider using a HashMap to store values you've already seen to achieve O(N) time complexity.";
        } else if (lower.contains("explain")) {
            return "This code iterates through the data structure. It appears to be an O(N) approach. Ensure you handle edge cases like empty inputs.";
        } else if (lower.contains("roadmap")) {
            return "[\"Master Data Structures\", \"Learn Advanced Algorithms\", \"Build System Design Skills\", \"Practice Mock Interviews\", \"Apply for Senior Roles\"]";
        } else if (lower.contains("interview")) {
            return "Can you describe a time you optimized a poorly performing system? What metrics did you use to measure success?";
        }
        return "Acknowledged. I have processed your input. My neural networks are operating in offline simulation mode due to missing API keys, but your logic appears sound.";
    }

    public String getCareerRecommendation(int analytical, int creative, int technical, int social) {
        String prompt = String.format(
            "Based on the following career assessment profile (scores out of 5), provide a short, 3-4 sentence career recommendation for a student. Be highly encouraging and mention specific 2-3 job roles. " +
            "Analytical: %d, Creative: %d, Technical: %d, Social: %d.", 
            analytical, creative, technical, social);
        
        String result = generateContent(prompt);
        return result != null ? result : "Your high technical and social attributes suggest a great fit for Technical Product Management.";
    }

    public String generateCertificateVerification(String studentName, String courseTitle) {
        String prompt = String.format(
            "Generate a unique, professional, and inspiring 2-line verification statement for a certificate awarded to %s for completing the course '%s'. Focus on their dedication and future potential.",
            studentName, courseTitle);
        
        String result = generateContent(prompt);
        return result != null ? result.trim() : "This certificate verifies the successful completion of all course requirements and demonstrates mastery of the subject matter.";
    }

    public JsonNode generateQuizJson(String topic) {
        String prompt = String.format(
            "Generate a technical quiz about '%s' in JSON format. Provide exactly 5 multiple choice questions. " +
            "The JSON should be an array of objects, each having: 'questionText', 'optionA', 'optionB', 'optionC', 'optionD', 'correctOption' (one of A, B, C, or D), and 'explanation'. " +
            "Return ONLY the raw JSON array, no other text or markdown formatting.",
            topic);
        
        String result = generateContent(prompt);
        if (result == null) return null;

        try {
            // Remove markdown code blocks if present
            String cleanJson = result.replaceAll("```json", "").replaceAll("```", "").trim();
            return objectMapper.readTree(cleanJson);
        } catch (Exception e) {
            System.err.println("Error parsing Quiz JSON: " + e.getMessage());
            return null;
        }
    }

    public String generateCaptchaCode() {
        String prompt = "Generate a random 5-character uppercase alphanumeric code. Do not include ambiguous characters like O, 0, I, or 1. RETURN ONLY THE 5 CHARACTERS, NO OTHER TEXT.";
        String result = generateContent(prompt);
        if (result == null) return null;
        
        // Sanitize: Keep only uppercase letters and numbers, pick first 5
        String sanitized = result.replaceAll("[^A-Z2-9]", "");
        if (sanitized.length() >= 5) {
            return sanitized.substring(0, 5);
        }
        return sanitized.isEmpty() ? null : sanitized;
    }
    public String chatAgent(String message, String history) {
        String prompt = String.format(
            "SYSTEM INSTRUCTION: You are the 'Neural Architect', an elite AI career strategist. " +
            "Your tone is professional, high-signal, and slightly futuristic (minimalist). " +
            "You have access to the user's career trajectory. Maintain context of the following conversation history.\n\n" +
            "HISTORY:\n%s\n\n" +
            "USER MESSAGE: %s\n\n" +
            "Response (concise, under 4 sentences):",
            history != null ? history : "No previous context.",
            message);
        
        String result = generateContent(prompt);
        return result != null ? result.trim() : "Neural connection unstable. Please try again.";
    }

    public String analyzeResume(String resumeText, String targetRole) {
        String prompt = String.format(
            "Perform a FAANG-level 'Resume Deep-Mapping' for the following. Target Role: %s. " +
            "Analyze for: 1. Impact metrics, 2. ATS keywords, 3. Architectural clarity. " +
            "Provide 3-4 bullet points of high-octane feedback. Resume Text: %s",
            targetRole, resumeText);
        
        return generateContent(prompt);
    }

    public String getInterviewQuestion(String role, String company) {
        String prompt = String.format(
            "Generate a complex, high-signal interview question for a %s role at %s. " +
            "Focus on either system design, behavioral leadership, or advanced technical concepts. " +
            "Return ONLY the question.",
            role, company);
        
        return generateContent(prompt);
    }

    public String getInterviewFeedback(String question, String answer, String role) {
        String prompt = String.format(
            "As an elite recruiter for top tech companies, provide expert feedback on this interview response. " +
            "Question: %s\nUser Answer: %s\nTarget Role: %s\n\n" +
            "Provide a score (0-100) and 2 sentences of critical refinement tips.",
            question, answer, role);
        
        return generateContent(prompt);
    }

    public String getCompanyIntelligence(String company) {
        String prompt = String.format(
            "Generate an 'Elite Selection Guide' for %s. Include: " +
            "1. Key cultural pillars, 2. Core technical stack preferences, 3. Most frequent interview focal points. " +
            "Keep it structured and data-dense. Use professional, futuristic terminology.",
            company);
        
        return generateContent(prompt);
    }

    public String generateCustomRoadmap(String goal) {
        String prompt = String.format(
            "Generate a customized learning roadmap for a user who wants to achieve: '%s'. " +
            "Return the roadmap as a valid JSON array of strings, where each string is a milestone or step. " +
            "Provide exactly 5 steps. RETURN ONLY THE JSON ARRAY.",
            goal);
        
        String result = generateContent(prompt);
        if (result == null) return "[\"Analyze prerequisites\", \"Acquire foundational knowledge\", \"Build practical projects\", \"Network with professionals\", \"Apply for roles\"]";
        
        try {
            String cleanJson = result.replaceAll("```json", "").replaceAll("```", "").trim();
            return cleanJson;
        } catch (Exception e) {
            return "[\"Analyze prerequisites\", \"Acquire foundational knowledge\", \"Build practical projects\", \"Network with professionals\", \"Apply for roles\"]";
        }
    }

    public String analyzeCode(String code, com.careercompass.ai.model.Problem problem, String status) {
        String prompt = String.format(
            "Analyze the following code submitted for the problem '%s' (Difficulty: %s). " +
            "The execution status is: %s. " +
            "Provide brief, encouraging feedback on the code quality and correctness.\n\nCode:\n%s",
            problem.getTitle(), problem.getDifficulty(), status, code);
        
        return generateContent(prompt);
    }

    public String suggestOptimization(String code, com.careercompass.ai.model.Problem problem) {
        String prompt = String.format(
            "For the following code submitted for the problem '%s', suggest one specific optimization (time or space complexity) if possible. " +
            "If it's already optimal, state that it is well-optimized. Keep it concise.\n\nCode:\n%s",
            problem.getTitle(), code);
        
        return generateContent(prompt);
    }

    public String explainCode(String code, com.careercompass.ai.model.Problem problem) {
        String prompt = String.format(
            "Explain the following code submitted for the problem '%s' in simple terms. " +
            "Focus on the logic and how it solves the problem. Keep it under 5 sentences.\n\nCode:\n%s",
            problem.getTitle(), code);
        
        return generateContent(prompt);
    }

    public String getHints(com.careercompass.ai.model.Problem problem) {
        String prompt = String.format(
            "Provide two helpful hints for solving the problem '%s' (Difficulty: %s). " +
            "Description: %s\n\nDo not provide the solution, just guidance.",
            problem.getTitle(), problem.getDifficulty(), problem.getDescription());
        
        return generateContent(prompt);
    }
}
