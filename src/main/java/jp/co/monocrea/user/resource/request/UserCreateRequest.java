package jp.co.monocrea.user.resource.request;

import jp.co.monocrea.user.service.command.UserCreateCommand;

public class UserCreateRequest {
    private String name;
    private String email;
    private String phone;
    private String address;

    public UserCreateCommand convertToUserCreateCommand() {
        return new UserCreateCommand(name, email, phone, address);
    }
}
