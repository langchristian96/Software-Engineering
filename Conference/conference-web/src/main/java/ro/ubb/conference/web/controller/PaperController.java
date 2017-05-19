package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.web.converter.PaperConverter;
import ro.ubb.conference.web.dto.EmptyJsonResponse;
import ro.ubb.conference.web.dto.PaperDto;
import ro.ubb.conference.web.dto.PapersDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anca.
 */

@RestController
public class PaperController {

    private static final Logger log = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperConverter paperConverter;

    @RequestMapping(value = "/papers", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public PapersDto getPapers() {
        log.trace("getPapers");

        List<Paper> papers = paperService.findAll();

        log.trace("getPapers: papers={}", papers);

        return new PapersDto(paperConverter.convertModelsToDtos(papers));
    }

    @RequestMapping(value = "papers/{paperId}", method = RequestMethod.PUT)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, PaperDto> updatePaper(
            @PathVariable final Long paperId,
            @RequestBody final Map<String, PaperDto> paperDtoMap) {
        log.trace("updatePaper: paperId={}, paperDtoMap={}", paperId, paperDtoMap);

        PaperDto paperDto = paperDtoMap.get("paper");
        Paper paper = paperService.updatePaper(paperId, paperDto.getTitle(),
                paperDto.getAuthor(), paperDto.getContent());

        Map<String, PaperDto> result = new HashMap<>();
        result.put("paper", paperConverter.convertModelToDto(paper));

        log.trace("updatePaper: result={}", result);

        return result;
    }

    @RequestMapping(value = "/papers", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, PaperDto> createPaper(
            @RequestBody final Map<String, PaperDto> paperDtoMap) {
        log.trace("createPaper: paperDtoMap={}", paperDtoMap);

        PaperDto paperDto = paperDtoMap.get("paper");
        Paper paper = paperService.createPaper(
                paperDto.getTitle(), paperDto.getAuthor(), paperDto.getContent());

        Map<String, PaperDto> result = new HashMap<>();
        result.put("paper", paperConverter.convertModelToDto(paper));

        log.trace("updatePaper: result={}", result);

        return result;
    }

    @RequestMapping(value = "papers/{paperId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePaper(@PathVariable final Long paperId) {
        log.trace("deletePaper: paperId={}", paperId);

        paperService.deletePaper(paperId);

        log.trace("deletePaper - method end");

        return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
