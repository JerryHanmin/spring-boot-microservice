package com.hm.sample.jgit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jerry on 2015/11/12 0012.
 */
@RestController
@RequestMapping(value = "git")
public class JGitController {
    @Autowired
    JGitRepository jGitRepository;

    @RequestMapping(value = "/pullRepos", method = RequestMethod.POST, produces = "application/hal+json;charset=UTF-8")
    public ResponseEntity pullRepos(@RequestParam(value = "label", required = false, defaultValue = "master") String label) {
        if (jGitRepository.pullRepos(label))
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        else
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/fachRepos", method = RequestMethod.POST, produces = "application/hal+json;charset=UTF-8")
    public ResponseEntity fachRepos() {
        if (jGitRepository.fetchRepos())
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        else
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET, produces = "application/hal+json;charset=UTF-8")
    public ResponseEntity findOne(@RequestParam(value = "label", required = false, defaultValue = "master") String label, String name) {
        if (StringUtils.isEmpty(name))
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        else {
            return new ResponseEntity<>(jGitRepository.findOne(name, label), HttpStatus.OK);
        }
    }
}
