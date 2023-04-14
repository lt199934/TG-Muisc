package net.ltbk.music.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.dao.SongcollectDao;
import net.ltbk.music.service.SongcollectService;
import org.springframework.stereotype.Service;

/**
 * 服务接口实现
 *
 * @author liutao
 * @since 2023-03-31 21:55:23
 * @description 由 Mybatisplus Code Generator 创建
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SongcollectServiceImpl implements SongcollectService {
    private final SongcollectDao songcollectDao;

}