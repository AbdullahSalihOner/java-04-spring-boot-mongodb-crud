package com.salih.service;

import com.salih.model.Tutorial;
import com.salih.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public List<Tutorial> getAllTutorials(String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null) {
            tutorialRepository.findAll().forEach(tutorials::add);
        } else {
            tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
        }

        return tutorials;
    }


    public Optional<Tutorial> getTutorialById(String id) {
        return tutorialRepository.findById(id);
    }



    public Tutorial createTutorial(Tutorial tutorial) {
        return tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
    }



    public Optional<Tutorial> updateTutorial(String id, Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return Optional.of(tutorialRepository.save(_tutorial));
        } else {
            return Optional.empty();
        }
    }


    public boolean deleteTutorial(String id) {
        try {
            tutorialRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<Tutorial> findByPublished() {
        return tutorialRepository.findByPublished(true);
    }
}
