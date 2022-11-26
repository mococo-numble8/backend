package com.mococo.api.account.controller;

import com.mococo.api.account.controller.request.AccountCreateRequest;
import com.mococo.api.account.controller.response.AccountCreateResponse;
import com.mococo.api.account.controller.response.AccountResponse;
import com.mococo.api.account.service.AccountService;
import com.mococo.api.common.constants.UrlConstants;
import java.net.URI;
import javax.validation.Valid;

import com.mococo.api.dto.ApiResponseDto;
import com.mococo.core.account.domain.entity.Account;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping(UrlConstants.ACCOUNTS)
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final AccountService accountService;

    @PostMapping(path = UrlConstants.JOIN)
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "join success", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class)) }),
            @ApiResponse(responseCode = "400", description = "join  fail", content = @Content)})

    public ResponseEntity<ApiResponseDto<AccountCreateResponse>> join(@Valid @RequestBody AccountCreateRequest request) {

        final AccountCreateResponse response = accountService.create(request);

        final URI createdResourceLocation = UriComponentsBuilder
            .fromPath(UrlConstants.ACCOUNTS)
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(createdResourceLocation).body(ApiResponseDto.create(response));
    }


    @PostMapping(path = UrlConstants.EMAILCHECK)
    @Operation(summary = "이메일 조회", description = "중복 된 이메일인지 확인합니다.")
    public ResponseEntity<Boolean> checkEmail(@Valid @RequestBody String email ) {
        boolean response = accountService.get(email);
        return  ResponseEntity.ok(response);
    }


    @GetMapping(UrlConstants.PATH_ID)
    public ResponseEntity<ApiResponseDto<AccountResponse>> get(@PathVariable Long id) {

        final AccountResponse response = accountService.get(id);

        return ResponseEntity.ok(ApiResponseDto.create(response));
    }
}
