package com.Sensors.Measurement.controller;

import com.Sensors.Measurement.Repository.MeasurementRepository;
import com.Sensors.Measurement.Repository.SensorRepository;
import com.Sensors.Measurement.entity.Measurement;
import com.Sensors.Measurement.entity.Sensor;
import com.Sensors.Measurement.service.measurementService.MeasurementService;
import com.Sensors.Measurement.service.sensorService.SensorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MeasurementControllerTest {


    @MockBean
    private SensorRepository sensorRepository;
    @MockBean
    private MeasurementRepository measurementRepository;
    @MockBean
    private SensorService sensorService;
    @MockBean
    private MeasurementService measurementService;
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
    void retrieveBySensorType() throws Exception {
        Page<Measurement> mockPage = new PageImpl<>(List.of(measurement));
        when(measurementService.paginationMeasurement(measurementService.findMeasurementBySensorType("Temperature"), PageRequest.of(0, 10))).thenReturn(mockPage);

        mockMvc.perform(get("/measurement/type")
                        .param("type","Temperature")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void createMeasurement() throws Exception {
        when(sensorService.getSensorById(1L)).thenReturn(sensor);
        when(measurementService.saveMeasurement(measurement)).thenReturn(measurement);


        mockMvc.perform(post("/measurement/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(1))
                        .content("{\"readingType\":\"Humidity\",\"readingValue\":\"40\",\"readingDate\":\"\",\"time\":\"1:52:40\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void paginateRetrieveBySensorLocation() throws Exception {
        when(sensorService.getSensorById(1L)).thenReturn(sensor);
        when(sensorRepository.findSensorByLocationEquals("athens")).thenReturn(List.of(sensor));
        when(sensorService.findSensorIdByLocation("athens")).thenReturn(List.of(measurement));


        mockMvc.perform(get("/measurement/location")
                        .param("location","athens")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void modifyMeasurement() throws Exception {
        when(measurementService.saveMeasurement(measurement)).thenReturn(measurement);
        when(measurementService.getMeasurementById(1L)).thenReturn(measurement);

        mockMvc.perform(put("/measurement/modify/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "      \"readingType\":\"Humidity\",\n" +
                        "      \"readingValue\":55,\n" +
                        "      \"readingDate\":\"2019-02-03\",\n" +
                        "      \"time\":\"1:52:40\"\n" +
                        "}")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Measurement modifed successfully")))
                .andDo(print())
                .andReturn();
        verify(measurementService, times(1)).saveMeasurement(Mockito.any(Measurement.class));

    }

    @Test
    void modifyMeasurementNotFound() throws Exception {
        when(measurementService.saveMeasurement(measurement)).thenReturn(measurement);
        when(measurementService.getMeasurementById(1L)).thenReturn(measurement);

        mockMvc.perform(put("/measurement/modify/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "      \"readingType\":\"Humidity\",\n" +
                                "      \"readingValue\":55,\n" +
                                "      \"readingDate\":\"2019-02-03\",\n" +
                                "      \"time\":\"1:52:40\"\n" +
                                "}")
                )
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Measurement not found")))
                .andDo(print())
                .andReturn();

    }
}