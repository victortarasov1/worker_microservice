package executor.service.model;


import java.time.LocalTime;


public record StepReport (
    LocalTime startTime,
    LocalTime endTime,
    String errorMessage,
    String action,
    String value
){

}