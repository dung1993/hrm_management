package com.cg.utils;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtils {
    public final String IMAGE_UPLOAD_FOLDER = "m4_PersonalManagement";
    public final String VIDEO_UPLOAD_FOLDER = "m4_PersonalManagement";

    public Map buildImageUploadParams(PersonalAvatar personalAvatar) {
        if (personalAvatar == null || personalAvatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, personalAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(Personal personal, String publicId) {
        if (personal == null || personal.getId() == null)
            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildVideoUploadParams(PersonalAvatar personalAvatar) {
        if (personalAvatar == null || personalAvatar.getId() == null)
            throw new DataInputException("Không thể upload video của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", VIDEO_UPLOAD_FOLDER, personalAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "video"
        );
    }

    public Map buildVideoDestroyParams(Personal personal, String publicId) {
        if (personal == null || personal.getId() == null)
            throw new DataInputException("Không thể destroy video của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "video"
        );
    }
}
