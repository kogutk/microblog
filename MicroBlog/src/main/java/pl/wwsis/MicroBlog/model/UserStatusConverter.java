package pl.wwsis.MicroBlog.model;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
	
	@Override
    public String convertToDatabaseColumn(UserStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public UserStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(UserStatus.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
	
	
	



