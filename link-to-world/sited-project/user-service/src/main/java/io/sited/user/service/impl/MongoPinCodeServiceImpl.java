package io.sited.user.service.impl;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.user.UserOptions;
import io.sited.user.api.user.PinCodeRequest;
import io.sited.user.domain.UserPinCodeTracking;
import io.sited.user.service.PinCodeProvider;
import io.sited.user.service.PinCodeService;
import org.bson.Document;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author chi
 */
public class MongoPinCodeServiceImpl implements PinCodeService {
    @Inject
    UserOptions userOptions;

    @Inject
    MongoRepository<UserPinCodeTracking> repository;

    @Inject
    PinCodeProvider pinCodeProvider;

    public boolean canSendPinCode(PinCodeRequest request) {
        return canSendPinCode("email", request.email, userOptions.pinCode.dayRate, 60)
                && canSendPinCode("phone", request.phone, userOptions.pinCode.dayRate, 60)
                && canSendPinCode("ip", request.ip, userOptions.pinCode.ipDayRate, 0);
    }

    @Override
    public String create(PinCodeRequest request) {
        String code = pinCodeProvider.get();
        UserPinCodeTracking userPinCodeTracking = new UserPinCodeTracking();
        userPinCodeTracking.id = UUID.randomUUID().toString();
        userPinCodeTracking.ip = request.ip;
        userPinCodeTracking.code = code;
        userPinCodeTracking.email = request.email;
        userPinCodeTracking.phone = request.phone;
        userPinCodeTracking.createdBy = request.requestBy;
        userPinCodeTracking.createdTime = LocalDateTime.now();
        repository.insert(userPinCodeTracking);
        return code;
    }

    private boolean canSendPinCode(String key, String value, int dayRate, int limitSecond) {
        if (value == null) {
            return true;
        }
        Document filter = new Document(key, value);
        FindView<UserPinCodeTracking> results = repository.query(filter).sort("created_time", true).limit(dayRate).find();
        if (results.items.isEmpty()) {
            return true;
        }
        Duration lastCreated = Duration.between(results.items.get(0).createdTime, LocalDateTime.now());
        return lastCreated.getSeconds() >= limitSecond && (results.items.size() < dayRate || Duration.between(results.items.get(results.items.size() - 1).createdTime, LocalDateTime.now()).toHours() > 24);
    }
}
