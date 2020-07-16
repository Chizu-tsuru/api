package com.chizu.tsuru.map_clustering.workspaces.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WorkspaceServiceTest {
    private final WorkspaceService ws;

    @Autowired
    public WorkspaceServiceTest(WorkspaceService ws) {
        this.ws = ws;
    }


    @Test
    void grid_square_size_should_return_a_double() {
        double latDiff = 0.0027;
        double longDiff = 0.0037;

        double wanted = 0.001;
        double result = ws.getGridSquareSize(latDiff, longDiff);

        assertThat(result).isEqualTo(wanted);
    }

    @Test
    void coordinates_round_should_return_a_double() {
        double result = ws.coordinatesRound(0.71561454);
        double expected = 0.71561;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_return_correct_latitude_diff_1() {
        double min, max, expected, result;

        min = 30;
        max = 40;
        expected = 10;
        result = ws.getAbsDiff(min, max, ws.LATITUDE);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_return_correct_latitude_diff_2() {
        double min, max, expected, result;

        min = -30;
        max = 40;
        expected = 70;
        result = ws.getAbsDiff(min, max, ws.LATITUDE);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_return_correct_latitude_diff_3() {
        double min, max, expected, result;

        min = 30;
        max = -40;
        expected = 70;
        result = ws.getAbsDiff(min, max, ws.LATITUDE);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_return_correct_latitude_diff_4() {
        double min, max, expected, result;
        min = -30;
        max = -40;
        expected = 10;
        result = ws.getAbsDiff(min, max, ws.LATITUDE);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_return_correct_longitude_diff_1() {
        double min, max, expected, result;

        min = 30;
        max = 40;
        expected = 10;
        result = ws.getAbsDiff(min, max, ws.LONGITUDE);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_return_correct_longitude_diff_2() {
        double min, max, expected, result;

        min = -30;
        max = 40;
        expected = 70;
        result = ws.getAbsDiff(min, max, ws.LONGITUDE);
        assertThat(result).isEqualTo(expected);

    }

    @Test
    void should_return_correct_longitude_diff_3() {
        double min, max, expected, result;


        min = 30;
        max = -40;
        expected = 290;
        result = ws.getAbsDiff(min, max, ws.LONGITUDE);
        assertThat(result).isEqualTo(expected);


    }

    @Test
    void should_return_correct_longitude_diff_4() {
        double min, max, expected, result;


        min = -30;
        max = -40;
        expected = 350;
        result = ws.getAbsDiff(min, max, ws.LONGITUDE);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_return_correct_longitude_diff_5() {
        double min, max, expected, result;


        min = 60;
        max = 40;
        expected = 340;
        result = ws.getAbsDiff(min, max, ws.LONGITUDE);
        assertThat(result).isEqualTo(expected);

    }

}