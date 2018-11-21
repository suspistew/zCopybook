package com.zthulj.zcopybook.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zthulj.zcopybook.model.Coordinates;
import com.zthulj.zcopybook.model.ValueNode;
import com.zthulj.zcopybook.model.ZCopyBook;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class ZConverter {
    private final ZCopyBook<Object> copyBook;
    private ObjectMapper mapperJson = new ObjectMapper();

    public String convertToJson(@NotNull final String positionalLine) throws JsonProcessingException {
        if(null == positionalLine)
            throw new IllegalArgumentException("positionalLine can't be null");

        if(positionalLine.length() != copyBook.getWaitedLength())
            throw new IllegalArgumentException("Waited length : " + copyBook.getWaitedLength() + ", actual positionnalLine length : "+ positionalLine.length());

        for(ValueNode<Object> valueNode : copyBook.getValueNodes()){
            Coordinates c = valueNode.getCoordinates();
            valueNode.setValue(positionalLine.substring(c.getStart(), c.getEnd()));
        }
        return mapperJson.writeValueAsString(copyBook.getRootNode());
    }
}
