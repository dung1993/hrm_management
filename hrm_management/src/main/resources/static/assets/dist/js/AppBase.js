class AppBase{
    static DOMAIN_SERVER = location.origin;
    static DOMAIN_API = this.DOMAIN_SERVER + "/api";
    static API_PERSONAL = this.DOMAIN_API + "/personals"
    static API_CLOUDINARY = 'https://res.cloudinary.com/dkomrvd0q/image/upload'
    static SCALE_IMAGE_W_80_H_80_Q_100 = 'c_limit,w_80,h_80,q_100';

    static SCALE_IMAGE_W_80_H_80_Q_85 = 'c_limit,w_80,h_80,q_85';

    static SweetAlert = class {
        static showDeleteConfirmDialog() {
            return Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            })
        }

        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 2000
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }

        static showError401() {
            Swal.fire({
                icon: 'error',
                title: 'Access Denied',
                text: 'Invalid credentials!',
            })
        }

        static showError403() {
            Swal.fire({
                icon: 'error',
                title: 'Access Denied',
                text: 'You are not authorized to perform this function!',
            })
        }
    }
}

class Personal {
    constructor(id,personalAvatar,fullName,dateOfBirth,exp,rpH,skill,position) {
        this.id = id;
        this.personalAvatar = personalAvatar;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.exp = exp;
        this.rpH = rpH;
        this.skill = skill;
        this.position = position;

    }
}

// class Position {
//     constructor(id,positionName) {
//         this.id = id;
//         this.positionName = positionName
//     }
// }

class PersonalAvatar{
    constructor(id, fileFolder, fileName, fileUrl) {
        this.id = id;
        this.fileFolder = fileFolder;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}


