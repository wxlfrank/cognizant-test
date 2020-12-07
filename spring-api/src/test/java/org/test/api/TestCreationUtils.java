package org.test.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.test.api.model.Task;
import org.test.api.repositories.TaskRepository;

public class TestCreationUtils {

    public static <T> T createEntity(ObjectMapper mapper, MockMvc mvc, String path, Class<T> clazz) {
        byte[] content = TestUtils.readFile(TestCreationUtils.class, path + ".json");
        try {
            mapper.readValue(content, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            MvcResult result = mvc.perform(post(path).content(content).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            return mapper.readValue(result.getResponse().getContentAsString(), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Task createTask(TaskRepository taskRepository, ObjectMapper mapper, MockMvc mvc) {
        byte[] content = TestUtils.readFile(TestCreationUtils.class, "/task.json");
        try {
            Task task = mapper.readValue(content, Task.class);
            content = mapper.writeValueAsBytes(task);
            return taskRepository.save(task);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
