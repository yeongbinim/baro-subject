package nbc.subjectbaro.domain.common.exception;

public record ExceptionResponse(
    ErrorResponse error
) {

    public static ExceptionResponse from(String code, String message) {
        return new ExceptionResponse(new ErrorResponse(code, message));
    }

    private record ErrorResponse(
        String code,
        String message
    ) {

    }
}
