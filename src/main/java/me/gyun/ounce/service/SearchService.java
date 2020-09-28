package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.logindto.UserProfileDto;
import me.gyun.ounce.mapper.SearchMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchService {

    private final SearchMapper searchMapper;

    /**
     * 생성자 의존성 주입
     *
     * @param SearchMapper
     */
    public SearchService(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }


    /**
     * 유저 검색
     *
     * @param id
     */
    public DefaultRes userSearch(String id) {
        try {
            List<UserProfileDto> userList = searchMapper.userSearch(id);
            if (userList.isEmpty()) {
                return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NO_SEARCH_RESULT);
            }

            return DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_SEARCH_USER, userList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
