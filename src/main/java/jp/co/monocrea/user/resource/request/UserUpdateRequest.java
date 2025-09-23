package jp.co.monocrea.user.resource.request;

import jp.co.monocrea.user.service.command.UserUpdateCommand;

public class UserUpdateRequest {
    private String name;
    private String email;
    private String phone;
    private String address;

    public UserUpdateCommand convertToUserUpdateCommand(Long id) {
        return new UserUpdateCommand(id, name, email, phone, address);
    }
}
