package org.einherjer.sample.controller;

import org.einherjer.sample.model.Asset;
import org.einherjer.sample.service.AssetService;
import org.einherjer.sample.service.exception.AssetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/v1")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    public @ResponseBody Iterable<Asset> getAssets() {
        return assetService.findAll();
    }

    @RequestMapping(value = "/assets/{assetId}", method = RequestMethod.GET)
    public @ResponseBody Asset getAsset(@PathVariable Long assetId) {
        return assetService.get(assetId);
    }
    
    @RequestMapping(value = "/assets", method = RequestMethod.POST)
    public ResponseEntity<String> postAsset(@RequestBody Asset jsonBody) throws AssetNotFoundException {
        this.validateAssetDTO(jsonBody);
        assetService.create(jsonBody);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/assets/{assetId}", method = RequestMethod.PUT)
    public ResponseEntity<String> putAsset(
            @RequestBody Asset jsonBody, @PathVariable Long assetId) throws AssetNotFoundException {
        this.validateAssetDTO(jsonBody);
        assetService.createOrUpdate(assetId, jsonBody);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
    
    private void validateAssetDTO(Asset jsonBody){
        Assert.notNull(jsonBody.getCode(), "Code cannot be null");
    }
    
    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionBody handleAssetNotFoundException(AssetNotFoundException e) {
        return new ExceptionBody(e.getMessage()/*, ExceptionUtils.getStackTrace(e)*/);
    }
    
    @RequestMapping(value = "/assets/{assetId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAsset(@PathVariable Long assetId) throws AssetNotFoundException {
        assetService.delete(assetId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
