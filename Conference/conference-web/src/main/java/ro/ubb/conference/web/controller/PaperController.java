package ro.ubb.conference.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.ubb.conference.core.domain.Author;
import ro.ubb.conference.core.domain.Paper;
import ro.ubb.conference.core.service.AuthorService;
import ro.ubb.conference.core.service.PaperService;
import ro.ubb.conference.core.service.ReviewerService;
import ro.ubb.conference.core.service.SessionService;
import ro.ubb.conference.web.converter.PaperConverter;
import ro.ubb.conference.web.dto.EmptyJsonResponse;
import ro.ubb.conference.web.dto.PaperDto;
import ro.ubb.conference.web.dto.PapersDto;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by anca.
 */

@RestController
public class PaperController {

    private static final Logger log = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private PaperService paperService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private AuthorService authorService;

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

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/papers/reviewer/{reviewerId}", method = RequestMethod.GET)
    public PapersDto getPapersOfReviewer(@PathVariable final Long reviewerId) {
        log.trace("getPapersOfReviewer");

        Set<Paper> papers = paperService.findAllPapersOfReviewer(reviewerId);

        log.trace("getPapersOfReviewer: papers={}", papers);

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
                paperDto.getAbstractText(), paperDto.getContentPath(), paperDto.getKeywords(), paperDto.getTopics(), authorService.findAllAuthorsByUsernames(paperDto.getAuthorsUsername()), reviewerService.findAllReviewersByUsernames(paperDto.getReviewersUsername()), null);

        Map<String, PaperDto> result = new HashMap<>();
        result.put("paper", paperConverter.convertModelToDto(paper));

        log.trace("updatePaper: result={}", result);

        return result;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "papers/file",method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<String> addPaperWithFile(@RequestPart("uploadFile") MultipartFile file, @RequestPart("paper") PaperDto paperDtoMap){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                PaperDto paperDto = paperDtoMap;
                // Creating the directory to store file
                String rootPath = "C:/work/Software-Engineering/Conference/conference-web/src/main/resources/files";
//                String rootPath = "../../../resources/files";
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());

                paperDto.setContentPath(serverFile.getAbsolutePath());
                paperService.updatePaper(paperDto.getId(), paperDto.getTitle(), paperDto.getAbstractText(),
                        paperDto.getContentPath(), paperDto.getKeywords(), paperDto.getTopics(), authorService.findAllAuthorsByUsernames(paperDto.getAuthorsUsername()), reviewerService.findAllReviewersByUsernames(paperDto.getReviewersUsername()), null);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new ResponseEntity<String>("You successfully uploaded file=" + file.getOriginalFilename(), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<String>("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<String>("You failed to upload " + file.getOriginalFilename()
                    + " because the file was empty.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/papers", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, PaperDto> createPaper(
            @RequestBody final Map<String, PaperDto> paperDtoMap) {
        log.trace("createPaper: paperDtoMap={}", paperDtoMap);

        PaperDto paperDto = paperDtoMap.get("paper");

        Paper paper = paperService.createPaper(
                paperDto.getTitle(), paperDto.getAbstractText(), paperDto.getContentPath(), paperDto.getKeywords(), paperDto.getTopics());

        paper = paperService.updatePaperAuthors(paper.getId(), authorService.findAllAuthorsByUsernames(paperDto.getAuthorsUsername()));

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
