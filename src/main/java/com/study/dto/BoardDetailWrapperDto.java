package com.study.dto;

import java.util.List;
import java.util.stream.Collectors;
import com.study.dto.items.BoardDetailDto;
import com.study.dto.items.CommentDto;
import com.study.dto.items.FileDto;
import com.study.entity.BoardDetailEntity;
import com.study.entity.CommentEntity;
import com.study.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailWrapperDto {
    private BoardDetailDto boardDetail;
    private List<CommentDto> comments;
    private List<FileDto> files;

    public static BoardDetailWrapperDto fromEntities(BoardDetailEntity boardDetailEntity,
            List<CommentEntity> commentEntities, List<FileEntity> fileEntities) {
        BoardDetailDto boardDetailDTO = new BoardDetailDto(boardDetailEntity.getBoardId(),
                boardDetailEntity.getCategory(), boardDetailEntity.getTitle(),
                boardDetailEntity.getRegDate(), boardDetailEntity.getUpdateDate());

        List<CommentDto> commentDTOs =
                commentEntities
                        .stream().map(comment -> new CommentDto(comment.getCommentId(),
                                comment.getContent(), comment.getRegDate()))
                        .collect(Collectors.toList());

        List<FileDto> fileDTOs = fileEntities.stream()
                .map(file -> new FileDto(file.getFilesId(), file.getAttachType(),
                        file.getByteSize(), file.getUuidName(), file.getOrgName(),
                        file.getFileDir()))
                .collect(Collectors.toList());

        return new BoardDetailWrapperDto(boardDetailDTO, commentDTOs, fileDTOs);
    }
}
