package de.elvirakraft.docmanagement.services;

import de.elvirakraft.docmanagement.entities.DocCategory;
import de.elvirakraft.docmanagement.repositories.DocCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocCategoryService {

    private final DocCategoryRepository docCategoryRepository;

    @Autowired
    public DocCategoryService(DocCategoryRepository docCategoryRepository){
        this.docCategoryRepository = docCategoryRepository;
    }

    /**
     * Adds the given document category to the database.
     *
     * @param docCategory The given category of a document.
     * @return The added document category.
     */
    public DocCategory addDocCategory(DocCategory docCategory) {
        return docCategoryRepository.save(docCategory);
    }

    /**
     * Edits the given document category.
     *
     * @param docCategory The given document category.
     * @return The edited document category.
     */
    public DocCategory updateDocCategory(DocCategory docCategory) {
        Optional<DocCategory> optionalDocCategory = docCategoryRepository.findById(docCategory.getId());
        if (optionalDocCategory.isEmpty()) {
            return null;
        }

        DocCategory docCategoryToUpdate = optionalDocCategory.get();
        if (docCategory.getCategory() != null) docCategoryToUpdate.setCategory(docCategory.getCategory());
       return docCategoryRepository.save(docCategoryToUpdate);
    }

    /**
     * Deletes the document category with the given ID.
     *
     * @param id The given ID.
     * @return The deleted document category.
     */
    public DocCategory deleteDocCategory(Integer id) {
        Optional<DocCategory> optionalDocCategory = docCategoryRepository.findById(id);
        if (optionalDocCategory.isEmpty()) {
            return null;
        }

        DocCategory docCategoryToDelete = optionalDocCategory.get();
        docCategoryToDelete.setDeleted(true);
        return docCategoryRepository.save(docCategoryToDelete);
    }

    /**
     * Returns the document category with the given ID.
     *
     * @param id The given ID.
     * @return The document category.
     */
    public Optional<DocCategory> getDocCategoryById(Integer id) {
        return docCategoryRepository.findById(id);
    }

    /**
     * Returns all document categories.
     *
     * @return The document categories.
     */
    public List<DocCategory> getAllDocCategories() {
        return docCategoryRepository.findAll();
    }


}
