package org.test.api.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.test.api.controllers.SolutionController.Top3Report;
import org.test.api.model.Solution;
import org.test.api.model.Task;
import org.test.api.repositories.SolutionRepository;
import org.test.api.repositories.TaskRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SolutionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreation() {
        Solution created = TestCreationUtils.createEntity(mapper, mvc, "/solution", Solution.class);
        Assert.notNull(created, "solution is null");
        Assert.notNull(created.getId(), "id of solution is null");
    }

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SolutionRepository solutionRepository;

    @Test
    public void testReport() {
        Task[] tasks = new Task[5];
        for (int i = 0; i < 3; i++) {
            Task task = new Task();
            tasks[i] = task;
            task.setName(i + "");
            taskRepository.save(task);
        }
        taskRepository.flush();

        Solution[] solutions = new Solution[15];
        for (int i = 0; i < 15; i++) {
            Solution solution = new Solution();
            solutions[i] = solution;
            solution.setName(i + "");
            solution.setTask(tasks[i % 3]);
            solution.setSuccess(i % 2 == 0);
            solutionRepository.save(solution);
        }
        solutionRepository.flush();
        Top3Report[] output = null;
        try {
            MvcResult result = mvc.perform(get("/solution/top3")).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
            output = mapper.readValue(result.getResponse().getContentAsString(), Top3Report[].class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        Assert.notNull(output, "find solution is null");
        Assert.isTrue(output.length == 8, "success solution is " + output.length + " not 8");

    }

}
