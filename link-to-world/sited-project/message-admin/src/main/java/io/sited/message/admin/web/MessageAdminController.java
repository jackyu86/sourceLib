package io.sited.message.admin.web;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import io.sited.http.Request;
import io.sited.http.exception.UnauthorizedException;
import io.sited.message.api.MessageWebService;
import io.sited.message.api.message.BatchDeleteRequest;
import io.sited.message.api.message.BatchReadRequest;
import io.sited.message.api.message.MessageQuery;
import io.sited.message.api.message.MessageResponse;
import io.sited.admin.AdminUser;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class MessageAdminController {
    @Inject
    MessageWebService messageWebService;

    @Path("/admin/ajax/message/find")
    @PUT
    public FindView<MessageResponse> find(Request request) throws IOException {
        MessageQuery query = request.body(MessageQuery.class);
        query.to = request.require(AdminUser.class).id;
        return messageWebService.find(query);
    }

    @Path("/admin/ajax/message/:id/read")
    @POST
    public void read(@PathParam("id") String id, Request request) throws IOException {
        MessageResponse message = messageWebService.get(id);
        String userId = request.require(AdminUser.class).id;
        if (message.to.equals(userId)) {
            messageWebService.read(id);
        } else {
            throw new UnauthorizedException("failed to read message, id={}, userId={}", id, userId);
        }
    }

    @Path("/admin/ajax/message/batch-read")
    @POST
    public void batchRead(Request request) throws IOException {
        BatchReadRequest batchReadRequest = request.body(BatchReadRequest.class);
        batchReadRequest.to = request.require(AdminUser.class).id;
        messageWebService.batchRead(batchReadRequest);
    }

    @Path("/admin/ajax/message/batch-delete")
    @POST
    public void batchDelete(Request request) throws IOException {
        BatchDeleteRequest batchDeleteRequest = request.body(BatchDeleteRequest.class);
        batchDeleteRequest.to = request.require(AdminUser.class).id;
        messageWebService.batchDelete(batchDeleteRequest);
    }


    @Path("/admin/ajax/message/:id")
    @DELETE
    public void delete(@PathParam("id") String id, Request request) throws IOException {
        MessageResponse message = messageWebService.get(id);
        String userId = request.require(AdminUser.class).id;
        if (message.to.equals(userId)) {
            messageWebService.delete(id);
        } else {
            throw new UnauthorizedException("failed to read message, id={}, userId={}", id, userId);
        }
    }
}
