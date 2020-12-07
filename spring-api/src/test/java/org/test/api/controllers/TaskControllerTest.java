package org.test.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.test.api.TestCreationUtils;
import org.test.api.model.Task;
import org.test.api.repositories.TaskRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreation() throws IOException, URISyntaxException {
        Task task = TestCreationUtils.createTask(taskRepository, mapper, mvc);
        Assert.notNull(task, "created task is null");
        Assert.notNull(task.getName(), "name of task is null");
    }

    @Test
    public void testfindTasks() {
        Task[] output = null;
        try {
            MvcResult result = mvc.perform(get("/task")).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
            output = mapper.readValue(result.getResponse().getContentAsString(), Task[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.notNull(output, "tasks is null");
    }
}
