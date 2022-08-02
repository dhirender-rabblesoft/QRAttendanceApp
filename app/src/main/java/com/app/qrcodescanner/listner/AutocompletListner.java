package com.app.qrcodescanner.listner;

import com.app.qrcodescanner.model.AuthoriseList;
import com.app.qrcodescanner.model.CareListJson;

public interface AutocompletListner
{
    void  autoclick(String type, AuthoriseList.Data authdata, CareListJson.Data caredata);
}
