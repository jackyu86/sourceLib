package app.dealer.policyholder.service;

import app.dealer.api.policyholder.CreatePolicyHolderRequest;
import app.dealer.api.policyholder.UpdatePolicyHolderRequest;
import app.dealer.policyholder.domain.PolicyHolder;
import io.sited.http.exception.NotFoundException;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author miller
 */
public class PolicyHolderService {
    @Inject
    MongoRepository<PolicyHolder> repository;

    public PolicyHolder create(CreatePolicyHolderRequest request) {
        PolicyHolder policyHolder = new PolicyHolder();
        policyHolder.dealerId = request.dealerId;
        policyHolder.name = request.name;
        policyHolder.gender = request.gender;
        policyHolder.birthDate = request.birthDate;
        policyHolder.idNumber = request.idNumber;
        policyHolder.phone = request.phone;
        policyHolder.email = request.email;
        policyHolder.createdBy = request.createdBy;
        policyHolder.createTime = LocalDateTime.now();
        policyHolder.updatedBy = request.createdBy;
        policyHolder.updateTime = LocalDateTime.now();
        repository.insert(policyHolder);
        return policyHolder;
    }

    public PolicyHolder update(ObjectId id, UpdatePolicyHolderRequest request) {
        Optional<PolicyHolder> original = findById(id);
        if (!original.isPresent()) {
            throw new NotFoundException("missing policyHolder,id={}", id);
        }
        PolicyHolder policyHolder = original.get();
        policyHolder.name = request.name;
        policyHolder.gender = request.gender;
        policyHolder.birthDate = request.birthday;
        policyHolder.idNumber = request.idNumber;
        policyHolder.phone = request.phone;
        policyHolder.email = request.email;
        policyHolder.updatedBy = request.updatedBy;
        policyHolder.updateTime = LocalDateTime.now();
        repository.update(id, policyHolder);
        return policyHolder;
    }

    public Optional<PolicyHolder> findById(ObjectId id) {
        PolicyHolder policyHolder = repository.get(id);
        return Optional.ofNullable(policyHolder);
    }

    public void delete(ObjectId id) {
        if (!findById(id).isPresent()) {
            throw new NotFoundException("missing policyHolder,id={}", id);
        }
        repository.delete(id);
    }

    public FindView<PolicyHolder> list(String dealerId) {
        Document filter = new Document("dealer_id", dealerId);
        return repository.query(filter).find();
    }
}
