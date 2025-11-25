package com.example.demo.docs;

import com.example.demo.entity.People;
import com.example.demo.exception.ErrorMessage;
import com.example.demo.web.dto.RequestNameUpdate;
import com.example.demo.web.dto.ResponsePeopleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "People", description = "Documentação da entidade Pessoa")
public interface PeopleDocs {

    @Operation(summary = "Create People", description = "Http para criar uma nova Pessoa",
            responses = {@ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Erro ao criar Pessoa!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<People> create(@Valid @RequestBody People people);

    @Operation(summary = "Find by Id", description = "Http para retornar Pessoa pelo id",
            responses = {@ApiResponse(responseCode = "200", description = "Sucesso ao buscar Pessoa pelo id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<ResponsePeopleDto> findById(@PathVariable Long id);

    @Operation(summary = "Find All", description = "Http para retornar todas as Pessoas",
            responses = {@ApiResponse(responseCode = "200", description = "Sucesso ao listar todas as Pessoas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoas não encontradas!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<List<ResponsePeopleDto>> findAll();

    @Operation(summary = "Update Name by Id", description = "Http para alterar o nome de uma pessoa pela busca do Id",
            responses = {@ApiResponse(responseCode = "200", description = "Sucesso ao Alterar o nome da Pessoa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<People> updateName(@PathVariable Long id, @RequestBody @Valid RequestNameUpdate nome);

    @Operation(summary = "Delete by Id", description = "Http para deletar Pessoa pelo id",
            responses = {@ApiResponse(responseCode = "204", description = "Sucesso ao deletar Pessoa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Erro ao deletar Pessoa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    ResponseEntity<Void> deletebeById(@PathVariable Long id);
}
