package com.Sensors.Measurement.controller;

import com.Sensors.Measurement.Repository.MeasurementRepository;
import com.Sensors.Measurement.Repository.SensorRepository;
import com.Sensors.Measurement.entity.Measurement;
import com.Sensors.Measurement.entity.Sensor;
import com.Sensors.Measurement.service.sensorService.SensorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SensorControllerTest {
    @MockBean
    private SensorService sensorService;

    @Autowired
    private MockMvc mockMvc;


    private Sensor sensor;
    private Measurement measurement;
    @BeforeEach
    public void setup(){
        this.sensor = new Sensor();
        sensor.setSensorId(1L);
        sensor.setType("Temperature Sensor");
        sensor.setVendorEmail("moi@hua.gr");
        sensor.setVendorName("Kostas");
        sensor.setDescription("thermokrasia");
        sensor.setLocation("athens");
        sensor.setPrice("234$");
        this.measurement = new Measurement();
        measurement.setId(1L);
        measurement.setSensorId(sensor);
        measurement.setReadingType("Temperature");
        measurement.setReadingValue(54L);
        List<Measurement> list= Arrays.asList(measurement);
        sensor.setMeasurements(list);
    }

    @Test
    void createSensor() throws Exception {
        when(sensorService.saveSensor(sensor)).thenReturn(sensor);

        mockMvc.perform(post("/sensor/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"Humidity Sensor\",\"vendorName\":\"giorgos\",\"vendorEmail\":\"giorgos@hua.gr\",\"description\":\"good machine\",\"location\":\"creta\",\"price\":\"650$\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllSensors() throws Exception {
        Page<Sensor> mockPage = new PageImpl<>(List.of(sensor));
        when(sensorService.findAllSensor(PageRequest.of(0, 10))).thenReturn(mockPage);

        mockMvc.perform(get("/sensor/findall")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Temperature Sensor")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Kostas")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("moi@hua.gr")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("thermokrasia")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("athens")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("234$")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void modifySensor() throws Exception {
        when(sensorService.saveSensor(sensor)).thenReturn(sensor);
        when(sensorService.getSensorById(1L)).thenReturn(sensor);

        mockMvc.perform(put("/sensor/modify/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sensorId\": 1,\"type\":\"Humidity Sensor\",\"vendorName\":\"giorgos\",\"vendorEmail\":\"giorgos@hua.gr\",\"description\":\"good machine\",\"location\":\"creta\",\"price\":\"650$\"}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Sensor modifed successfully")))
                .andDo(print())
                .andReturn();
    }
    @Test
    void modifySensorNotfound() throws Exception {
        when(sensorService.saveSensor(sensor)).thenReturn(sensor);
        when(sensorService.getSensorById(1L)).thenReturn(sensor);

        mockMvc.perform(put("/sensor/modify/{id}",2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sensorId\": 1,\"type\":\"Humidity Sensor\",\"vendorName\":\"giorgos\",\"vendorEmail\":\"giorgos@hua.gr\",\"description\":\"good machine\",\"location\":\"creta\",\"price\":\"650$\"}")
                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Sensor not found")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteSensor() throws Exception {
        mockMvc.perform(delete("/sensor/delete/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Sensor deleted successfully")))
                .andReturn();
    }
}