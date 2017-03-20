package hu.bme.mit.train.sensor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorTest {

    private TrainUser mockUser;
	private TrainController stubController;
	private TrainSensor sensor;
	
	@Before
    public void init() {
        mockUser = mock(TrainUser.class);
        stubController = mock(TrainController.class);
        sensor = new TrainSensorImpl(stubController, mockUser);
        sensor.overrideSpeedLimit(50);
       
    }
	
    @Test
    public void NormalFunctionalityTest() {
    	when(stubController.getReferenceSpeed()).thenReturn(50);
    	
    	sensor.overrideSpeedLimit(40);
    	verify(mockUser, times(0)).setAlarmState(true);
    }
    
    @Test
    public void NegativeSpeedLimitTest() {
    	when(stubController.getReferenceSpeed()).thenReturn(50);
    	
    	sensor.overrideSpeedLimit(-5);
    	verify(mockUser, times(1)).setAlarmState(true);
    }
    
    @Test
    public void TooHighSpeedLimitTest() {
    	when(stubController.getReferenceSpeed()).thenReturn(50);
    	
    	sensor.overrideSpeedLimit(505);
    	verify(mockUser, times(1)).setAlarmState(true);
    }
    
    @Test
    public void MoreThan50PercentSpeedLimitTest() {
    	when(stubController.getReferenceSpeed()).thenReturn(50);
    	
    	sensor.overrideSpeedLimit(20);
    	verify(mockUser, times(1)).setAlarmState(true);
    }
}
