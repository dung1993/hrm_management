const page = {
    urls: {
        findPersonalById: AppBase.API_PERSONAL,
        doCreate: AppBase.API_PERSONAL + "/create",
        doDelete: AppBase.API_PERSONAL + "/delete",
        doUpdate: AppBase.API_PERSONAL + "/update",
        doEveryThing: AppBase.API_PERSONAL,

    },
    elements: {},
    loadData: {},
    commands: {},
    dialogs: {
        elements: {},
        loadData: {},
        commands: {},
    }
}

let currentpersonalId;

let currentPageNumber = 0;
let keyWord = $('#keyWord').val();
let column = "";
let orderBy = "";

let personal = new Personal();
let personalAvatar = new PersonalAvatar();

page.dialogs.elements.frmPersonalCre = $('#frmPersonalCre');
page.dialogs.elements.frmPersonalUp = $('#frmPersonalUp');

page.elements.btnShowCreateModal = $('#btnShowCreateModal');

page.dialogs.elements.modalCreatePersonal = $('#modalCreatePersonal');
page.dialogs.elements.modalUpdatePersonal = $('#modalUpdatePersonal');

page.dialogs.elements.btnPersonalCre = $('#btnPersonalCre');
page.dialogs.elements.btnPersonalUp = $('#btnPersonalUp');
page.dialogs.elements.btnSearch = $('#btnSearch')

page.dialogs.elements.btnSort = $('#btnSort');

page.dialogs.elements.asc_name = $('#asc_name');
page.dialogs.elements.desc_name = $('#desc_name');

page.dialogs.elements.sort_column = $('#sort_column');
page.dialogs.elements.sort_orderBy = $('#sort_orderBy');

page.dialogs.elements.keyWord = $('#keyWord');

page.dialogs.elements.fullNameCre = $("#fullNameCre");
page.dialogs.elements.dateOfBirthCre = $("#dateCre");
page.dialogs.elements.expCre = $("#expCre");
page.dialogs.elements.rpHCre = $("#rpHCre");
page.dialogs.elements.skillCre = $("#skillCre");
page.dialogs.elements.positionCre = $("#positionCre");

page.dialogs.elements.fullNameUp = $("#fullNameUp");
page.dialogs.elements.dateOfBirthUp = $("#dateUp");
page.dialogs.elements.expUp = $("#expUp");
page.dialogs.elements.rpHUp = $("#rpHUp");
page.dialogs.elements.skillUp = $("#skillUp");
page.dialogs.elements.positionUp = $("#positionUp");

page.dialogs.elements.wrapper = $("section .wrapper");
page.dialogs.elements.wrapperContent = $("section .wrapper .content");
page.dialogs.elements.imagePreview = $("section .image-preview");
page.dialogs.elements.btnClearImagePreview = $(".clear-image-preview i");

page.dialogs.elements.imageFile = $("#imageFile");
page.dialogs.elements.imagePreviewCanvas = $("section .image-preview canvas");
page.dialogs.elements.canvas = $("#canvas");
page.dialogs.elements.context = page.dialogs.elements.canvas[0].getContext('2d');
page.dialogs.elements.imagePreviewCanvas.css("display", "none");
page.dialogs.elements.divImagePreview = $("#modalCreatePersonal div.image-preview");
page.dialogs.elements.divFileName = $("#modalCreatePersonal div.file-name");

page.dialogs.elements.imageFileUp = $("#imageFileUp");
page.dialogs.elements.imagePreviewCanvasUp = $("section .image-preview #canvasUp");
page.dialogs.elements.canvasUp = $("#canvasUp");
page.dialogs.elements.contextUp = page.dialogs.elements.canvasUp[0].getContext('2d');
page.dialogs.elements.imagePreviewCanvasUp.css("display", "none");
page.dialogs.elements.divImagePreviewUp = $("#modalUpdatePersonal div.image-preview");
page.dialogs.elements.divFileNameUp = $("#modalUpdatePersonal div.file-name");

page.dialogs.commands.doEveryThing = () => {

    $.ajax({
        type: 'POST',
        contentType: false,
        cache: false,
        processData: false,
        url: page.urls.doEveryThing + '/kw=' + keyWord + '&page=' + currentPageNumber + '&sort=' + column + ',' + orderBy,
    })
        .done((data) => {

            $('#tbPersonal tbody tr').remove();

            listPersonal = data.content;

            currentPageNumber = data.pageable.pageNumber;

            totalPages = data.totalPages;

            selectedPage = data.pageable.pageNumber;

            totalElements = data.totalElements;

            $.each(listPersonal, (i, item) => {

                personal = item;
                personalAvatar.fileFolder = item.fileFolder;
                personalAvatar.fileName = item.fileName

                let str = renderpersonal(personal, personalAvatar);
                $('#tbPersonal tbody').append(str);

                let numberOrder = 0;

                if( column == 'id' & orderBy == 'asc'){
                    numberOrder = (5 * (totalPages-currentPageNumber-1)) - i + 2;
                } else {
                    numberOrder = (5 * currentPageNumber) + i + 1;
                }

                $('#td_' + personal.id).append(numberOrder);

            })

            // for (let i = totalElements; i > 0; i--){
            //     let numberOrder = (5 * currentPageNumber) + i + 1;
            //     $('#td_' + personal.id).append(numberOrder);
            // }



            let strPage = renderPagination(totalPages, selectedPage);
            $('#renderPage').empty().append(strPage);

            page.commands.addEventClick();

            toastSuccess("Result");

        })
        .fail(() => {
            toastError("Result Fail");
        })
}

page.dialogs.commands.doUpdate = () => {
    let fullName = page.dialogs.elements.fullNameUp.val();
    let position = page.dialogs.elements.positionUp.val();
    let dateOfBirth = page.dialogs.elements.dateOfBirthUp.val();
    let exp = page.dialogs.elements.expUp.val();
    let skill = page.dialogs.elements.skillUp.val();
    let rpH = page.dialogs.elements.rpHUp.val();

    let avatarFile = page.dialogs.elements.imageFileUp[0].files[0];

    if (avatarFile == undefined) {
        avatarFile = null;
    }

    let formData = new FormData();
    formData.append("name", fullName);
    formData.append("author", position);
    formData.append("price", dateOfBirth);
    formData.append("quantity", exp);
    formData.append("price", skill);
    formData.append("quantity", rpH);
    formData.append("avatarFile", avatarFile);

    $.ajax({
        type: 'POST',
        contentType: false,
        cache: false,
        processData: false,
        url: page.urls.doUpdate + '/' + currentpersonalId,
        data: formData,
    })
        .done((data) => {

            personal = data;
            personalAvatar = data.personalAvatar;

            let str = renderpersonal(personal, personalAvatar);
            $('#tr_' + currentpersonalId).replaceWith(str);

            toastSuccess("Update personal successfully");

            page.dialogs.elements.modalUpdatePersonal.modal('hide');

            page.commands.addEventClick();

        })
        .fail((jqXHR) => {
            let statusCode = jqXHR.status;
            if (statusCode === 404) {
                toastError('personal not found');
            } else {
                toastError("Create personal fail");
            }
        })
}

page.dialogs.commands.doCreate = () => {
    let fullName = page.dialogs.elements.fullNameCre.val();
    let position = page.dialogs.elements.positionCre.val();
    let dateOfBirth = page.dialogs.elements.dateOfBirthCre.val();
    let exp = page.dialogs.elements.expCre.val();
    let skill = page.dialogs.elements.skillCre.val();
    let rpH = page.dialogs.elements.rpHCre.val();

    let avatarFile = page.dialogs.elements.imageFile[0].files[0];

    if (avatarFile == undefined) {
        avatarFile = null;
    }

    let formData = new FormData();
    formData.append("name", fullName);
    formData.append("author", position);
    formData.append("price", dateOfBirth);
    formData.append("quantity", exp);
    formData.append("price", skill);
    formData.append("quantity", rpH);
    formData.append("avatarFile", avatarFile);



    $.ajax({
        type: 'POST',
        contentType: false,
        cache: false,
        processData: false,
        url: page.urls.doCreate,
        data: formData,
    })
        .done((data) => {

            page.dialogs.commands.doEveryThing();

            page.dialogs.elements.modalCreatePersonal.modal('hide');

            toastSuccess("Create personal successfully");

        })
        .fail((jqXHR) => {
            let statusCode = jqXHR.status;
            if (statusCode === 409) {
                toastError('The name of personal is exists');
            }
            else if(statusCode === 406){
                toastError('Image must be JPG or PNG');
            }
            else if(statusCode === 413){
                toastError('Image size to large');
            }
            else {
                toastError("Create personal fail");
            }
        })
}

$(".modal").on('hidden.bs.modal', () => {
    $('.modal form').validate().resetForm();
    page.dialogs.elements.frmPersonalCre[0].reset();
    page.dialogs.elements.frmPersonalUp[0].reset();
    $(".modal .modal-alert-danger").removeClass("show").addClass("hide");
    $('.modal .modal-alert-danger').empty().removeClass("show").addClass("hide");
    $('.modal form').find("input.error").removeClass("error");
})

page.dialogs.elements.frmPersonalCre.validate({
    rules: {
        fullNameCre: {
            required: true,
        },
        positionCre: {
            required: true,
        },
        expCre: {
            required: true,
            number: true,
            min: 1,
            max: 50
        },
        rpHCre: {
            required: true,
            number: true,
            min: 1,
            max: 10000
        },
        skillCre: {
            required: true
        },
        avatarFile: {
            required: true
        }
    },
    messages: {
        fullNameCre: {
            required: "Full Name is required",
        },
        positionCre: {
            required: "Position is required",
        },
        expCre: {
            required: "Exp is required",
            number: "Exp must be number",
            min: "Exp min is 1 year",
            max: "Exp max is 50 year",
        },
        rpHCre: {
            required: "RpH is required",
            number: "RpH must be number",
            min: "RpH min is 1 $",
            max: "RpH max is 10000 $",
        },
        skillCre: {
            required: "Skill is required"
        },
        avatarFile: {
            required: "Avatar is required"
        }
    },
    errorLabelContainer: "#modalCreatePersonal .modal-alert-danger",
    errorPlacement: function (error, element) {
        error.appendTo("#modalCreatePersonal .modal-alert-danger");
    },
    showErrors: function (errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            $("#modalCreatePersonal .modal-alert-danger").removeClass("hide").addClass("show");
        } else {
            $("#modalCreatePersonal .modal-alert-danger").removeClass("show").addClass("hide").empty();
            $("#frmPersonalCre input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.commands.doCreate();
    }
});

page.dialogs.elements.frmPersonalUp.validate({
    rules: {
        fullNameUp: {
            required: true,
        },
        positionUp: {
            required: true,
        },
        expUp: {
            required: true,
            number: true,
            min: 1,
            max: 50
        },
        rpHUp: {
            required: true,
            number: true,
            min: 1,
            max: 10000
        },
        skilUp: {
            required: true
        },
        avatarFile: {
            required: true
        }
    },
    messages: {
        fullNameUp: {
            required: "Full Name is required",
        },
        positionUp: {
            required: "Position is required",
        },
        expUp: {
            required: "Exp is required",
            number: "Exp must be number",
            min: "Exp min is 1 year",
            max: "Exp max is 50 year",
        },
        rpHUp: {
            required: "RpH is required",
            number: "RpH must be number",
            min: "RpH min is 1 $",
            max: "RpH max is 10000 $",
        },
        skillUp: {
            required: "Skill is required"
        },
        avatarFile: {
            required: "Avatar is required"
        }
    },
    errorLabelContainer: "#modalUpdatePersonal .modal-alert-danger",
    errorPlacement: function (error, element) {
        error.appendTo("#modalUpdatePersonal .modal-alert-danger");
    },
    showErrors: function (errorMap, errorList) {
        if (this.numberOfInvalids() > 0) {
            $("#modalUpdatePersonal .modal-alert-danger").removeClass("hide").addClass("show");
        } else {
            $("#modalUpdatePersonal .modal-alert-danger").removeClass("show").addClass("hide").empty();
            $("#frmPersonalUp input.error").removeClass("error");
        }
        this.defaultShowErrors();
    },
    submitHandler: function () {
        page.dialogs.commands.doUpdate();
    }
});

page.dialogs.elements.btnPersonalCre.on('click' , () => {
    page.dialogs.elements.frmPersonalCre.trigger('submit');
})

page.dialogs.elements.btnPersonalUp.on('click' , () => {
    page.dialogs.elements.frmPersonalUp.trigger('submit');
})

page.dialogs.elements.btnSearch.on('click' , () => {
    keyWord = page.dialogs.elements.keyWord.val();
    page.dialogs.commands.doEveryThing();
})

page.loadData.findPersonalById = (id) => {
    return $.ajax({
        type: 'GET',
        url: page.urls.findPersonalById + '/' + id
    })
        .done((data) => {

        })
        .fail(() => {
            ;
        })
}

page.commands.addEventClick = () => {
    page.commands.addEventEditClick();
    page.commands.addEventDeleteClick();
    page.commands.handleAddEventPageLink();
    page.commands.addEventSort();
}

page.commands.addEventEditClick = () => {

    $('.edit').off('click');

    $('.edit').on('click', function () {

        $('#frmPersonalUp')[0].reset();

        let personalId = $(this).data('id');

        page.loadData.findPersonalById(personalId).then((data) => {

            personal = data;
            personalAvatar.fileFolder = data.fileFolder;
            personalAvatar.fileName = data.fileName;
            personalAvatar.fileUrl = data.fileUrl;

            currentpersonalId = personalId;

            page.dialogs.elements.fullNameUp.val(personal.fullName);
            page.dialogs.elements.positionUp.val(personal.position);
            page.dialogs.elements.dateOfBirthUp.val(personal.dateOfBirth);
            page.dialogs.elements.expUp.val(personal.exp);
            page.dialogs.elements.skillCre.val(personal.skill);
            page.dialogs.elements.rpHUp.val(personal.rpH);

            page.dialogs.commands.loadImageToCanvas(null, "URL", personalAvatar.fileUrl);

            page.dialogs.elements.modalUpdatePersonal.modal('show');


        })
            .catch(() => {
                toastError("personal id not exist");
            });
    })
}

page.commands.addEventDeleteClick = () => {

    $('.delete').off('click');

    $('.delete').on('click', function () {

        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {

                let personalId = $(this).data('id');

                page.loadData.findPersonalById(personalId).then((data) => {
                    $.ajax({
                        headers: {
                            'accept': 'application/json',
                            'content-type': 'application/json'
                        },
                        type: 'POST',
                        url: page.urls.doDelete + '/' + personalId,
                        // data: JSON.stringify(obj)
                    })
                        .done((data) => {

                            $('#tr_' + personalId).remove();

                            currentPageNumber = $('#pageActive').data('page');

                            page.dialogs.commands.doEveryThing();

                            page.commands.addEventClick();

                            Swal.fire({
                                position: 'middle-end',
                                icon: 'success',
                                title: 'Deleted Successfully',
                                showConfirmButton: false,
                                timer: 2000
                            })
                        })
                        .fail((error) => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'You dont have permition to do this',
                                showConfirmButton: true
                            })
                        })
                })
            }
        })
            .catch(() => {
                toastError("personal id not exist");
            });


    })
}

function renderpersonal(personal, personalAvatar) {
    let image_thumbnail = `
                ${AppBase.API_CLOUDINARY}/${AppBase.SCALE_IMAGE_W_80_H_80_Q_100}/${personalAvatar.fileFolder}/${personalAvatar.fileName}
            `;
    return `
                    <tr id="tr_${personal.id}" class="hover" style="font-weight: 600;">
                                    <td style="padding-left:10px" id="td_${personal.id}"></td>
                                    <td><img src="${image_thumbnail}" alt=""></td>
                                    <td>${personal.fullName}</td>
                                    <td>${personal.position}</td>
                                    <td>${personal.dateOfBirth}</td>
                                    <td>${personal.exp}</td>
                                    <td>${personal.skill}</td>
                                    <td>${personal.rpH}</td>
                                    <td style="display: flex; justify-content: center; align-items: center; padding-top: 27px">
                                        <div class="edit" data-id="${personal.id}">
                                            <i class="fa-regular fa-pen-to-square"  title="Edit"></i>
                                        </div>
                                        |
                                        <div class="delete" data-id="${personal.id}">
                                            <i class="fa-regular fa-trash-can" title="Delete"></i>
                                        </div>
                                    </td>
                                </tr>
                `;
}

function renderPagination(totalPages, selectedPage) {

    let str = "";


    if (currentPageNumber != 0) {
        str += `
                            <li class="page-item">
                                <a class="page-link " href="#" aria-label="Previous" data-page="${currentPageNumber-1}">
                                    <span aria-hidden="true">&laquo;</span>
                                    <span class="sr-only">Previous</span>
                                </a>
                            </li>
                        `;
    }

    for(let i = 1; i <= totalPages ; i++ ) {
        if ((i - 1 ) === selectedPage) {
            str += `
                    <li class="page-item active" style="display: flex">
                        <a id="pageActive" class="page-link" href="#" data-page="${i-1}">${i}</a>
                    </li>
                `;
        }
        else {
            str += `
                    <li class="page-item" style="display: flex">
                        <a class="page-link" href="#" data-page="${i-1}">${i}</a>
                    </li>
                `;
        }
    }

    if(currentPageNumber != (totalPages - 1)){
        str += `
                    <li class="page-item">
                        <a class="page-link" href="#" aria-label="Next" data-page="${currentPageNumber+1}">
                            <span aria-hidden="true">&raquo;</span>
                            <span class="sr-only">Next</span>
                        </a>
                    </li>
                `;
    }

    return str;
}

page.commands.handleAddEventPageLink = () => {
    $('.page-link').on('click', function () {

        let pageNumber = $(this).data('page');

        currentPageNumber = pageNumber;

        keyWord = page.dialogs.elements.keyWord.val();

        page.dialogs.commands.doEveryThing()
    })
}

page.commands.addEventSort = () => {
    $('.sort').off('click');

    $('.sort').on('click', function () {

        $('.sort').removeClass('sort-active');
        $(this).addClass('sort-active');

        page.dialogs.elements.sort_column.val($(this).data('column'));
        page.dialogs.elements.sort_orderBy.val($(this).data('ascdesc'));

        column =  page.dialogs.elements.sort_column.val();
        orderBy = page.dialogs.elements.sort_orderBy.val();

        keyWord = page.dialogs.elements.keyWord.val();

        page.dialogs.commands.doEveryThing();
    })
}

page.dialogs.commands.loadImageToCanvas = (imageFile, fileType, imageUrl) => {
    page.dialogs.elements.imagePreviewCanvas.css("display", "");
    page.dialogs.elements.wrapper.addClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 0);

    page.dialogs.elements.imagePreviewCanvasUp.css("display", "");
    page.dialogs.elements.wrapper.addClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 0);

    let imageObj = new Image();

    imageObj.onload = function () {
        page.dialogs.elements.context.canvas.width = imageObj.width;
        page.dialogs.elements.context.canvas.height = imageObj.height;
        page.dialogs.elements.context.drawImage(imageObj, 0, 0, imageObj.width, imageObj.height);

        page.dialogs.elements.contextUp.canvas.width = 445;
        page.dialogs.elements.contextUp.canvas.height = 345;
        page.dialogs.elements.contextUp.drawImage(imageObj, 0, 0, 445, 345);
    };

    if (fileType === "BINARY") {
        imageObj.src = URL.createObjectURL(imageFile);
    }
    else {
        imageObj.src = imageUrl;
    }
}

page.dialogs.commands.changeImagePreview = (elem) => {
    let imageFile = elem[0].files[0];

    if (imageFile) {
        let reader = new FileReader();

        reader.readAsDataURL(imageFile);

        reader.onload = function (e) {
            if (e.target.readyState === FileReader.DONE) {
                page.dialogs.commands.loadImageToCanvas(imageFile, "BINARY", null);
            }
        }
    }
    else {
        page.dialogs.commands.clearImagePreview();
    }
}

page.dialogs.commands.clearImagePreview = () => {
    page.dialogs.elements.imageFile.val("");
    page.dialogs.elements.imagePreviewCanvas.css("display", "none");
    page.dialogs.elements.wrapper.removeClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 1);
}

page.commands.loadData = () => {
    page.dialogs.commands.doEveryThing();
}

page.initializeControlEvent = () => {

    page.dialogs.elements.divImagePreview.on('click', () => {
        page.dialogs.elements.imageFile.trigger('click');
    })

    page.dialogs.elements.divFileName.on('click', () => {
        page.dialogs.elements.imageFile.trigger('click');
    })

    page.dialogs.elements.divImagePreviewUp.on('click', () => {
        page.dialogs.elements.imageFileUp.trigger('click');
    })

    page.dialogs.elements.divFileNameUp.on('click', () => {
        page.dialogs.elements.imageFileUp.trigger('click');
    })

    page.dialogs.elements.imageFile.on("change", function () {
        page.dialogs.commands.changeImagePreview(page.dialogs.elements.imageFile);
    });

    page.dialogs.elements.imageFileUp.on("change", function () {
        page.dialogs.commands.changeImagePreview(page.dialogs.elements.imageFileUp);
    });

    page.dialogs.elements.btnClearImagePreview.on('click', () => {
        page.dialogs.commands.clearImagePreview();
    })

    page.elements.btnShowCreateModal.on('click', () => {
        $('#frmPersonalCre')[0].reset();
        page.dialogs.commands.clearImagePreview();
        page.dialogs.elements.modalCreatePersonal.modal('show');
    })


}

$(() => {
    page.commands.loadData();

    page.initializeControlEvent();
})
