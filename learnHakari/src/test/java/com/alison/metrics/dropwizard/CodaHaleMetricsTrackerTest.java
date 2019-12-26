package com.alison.metrics.dropwizard;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.metrics.dropwizard.CodaHaleMetricsTracker;
//import com.zaxxer.hikari.mocks.StubPoolStats;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * @Author alison
 * @Date 2019/12/23  16:12
 * @Version 1.0
 * @Description
 */
@RunWith(MockitoJUnitRunner.class)
public class CodaHaleMetricsTrackerTest {


    @Mock
    public MetricRegistry mockMetricRegistry;

    private CodaHaleMetricsTracker testee;

    @Before
    public void setup() {
//        testee = new CodaHaleMetricsTracker("mypool", new StubPoolStats(0), mockMetricRegistry);
    }

    @Test
    public void close() {
        testee.close();

        verify(mockMetricRegistry).remove("mypool.pool.Wait");
        verify(mockMetricRegistry).remove("mypool.pool.Usage");
        verify(mockMetricRegistry).remove("mypool.pool.ConnectionCreation");
        verify(mockMetricRegistry).remove("mypool.pool.ConnectionTimeoutRate");
        verify(mockMetricRegistry).remove("mypool.pool.TotalConnections");
        verify(mockMetricRegistry).remove("mypool.pool.IdleConnections");
        verify(mockMetricRegistry).remove("mypool.pool.ActiveConnections");
        verify(mockMetricRegistry).remove("mypool.pool.PendingConnections");
        verify(mockMetricRegistry).remove("mypool.pool.MaxConnections");
        verify(mockMetricRegistry).remove("mypool.pool.MinConnections");
    }
}
