<?php 

    class DbOperations{

        private $con; 

        function __construct(){
            require_once dirname(__FILE__) . '/DbConnect.php';
            $db = new DbConnect; 
            $this->con = $db->connect(); 
        }

        public function createUser($nom, $prenom , $age , $email, $niveau,$image, $username,  $password, $changedpw){
           if(!$this->isEmailExist($email)){
                $stmt = $this->con->prepare("INSERT INTO students (nom, prenom, age, email, niveau ,image, username , password , changedpw) VALUES (?,?,?,?,?,?,?,?,?)");
                $stmt->bind_param("sssssssss", $nom, $prenom , $age , $email, $niveau,$image, $username,  $password , $changedpw);
                if($stmt->execute()){
                    return USER_CREATED; 
                }else{
                    return USER_FAILURE;
                }
           }
           return USER_EXISTS; 
        }

        public function userLogin($username, $password){
            if($this->usernameExists($username)){
                $hashed_password = $this->getUsersPasswordByUsername($username); 
                if(password_verify($password, $hashed_password)){
                    return USER_AUTHENTICATED;
                }
                else
                {
                    return USER_PASSWORD_DO_NOT_MATCH; 
                }
            }
            else
            {
                return USER_NOT_FOUND; 
            }
        }

        private function getUsersPasswordByUsername($usr){
            $stmt = $this->con->prepare("SELECT password FROM students WHERE username = ?");
            $stmt->bind_param("s", $usr);
            $stmt->execute(); 
            $stmt->bind_result($password);
            $stmt->fetch(); 
            return $password; 
        }

        public function getUserIdByUsername($usr){
            $stmt = $this->con->prepare("SELECT idEtudiant FROM students WHERE username = ?");
            $stmt->bind_param("s", $usr);
            $stmt->execute(); 
            $stmt->bind_result($id);
            $stmt->fetch(); 
            return $id; 
        }

        public function getNiveauById($id){
            $stmt = $this->con->prepare("SELECT niveau FROM students WHERE idEtudiant = ?");
            $stmt->bind_param("i", $id);
            $stmt->execute(); 
            $stmt->bind_result($niveau);
            $stmt->fetch(); 
            return $niveau; 
        }

        public function getAllUsers(){
            $stmt = $this->con->prepare("SELECT id, email, name, school FROM users;");
            $stmt->execute(); 
            $stmt->bind_result($id, $email, $name, $school);
            $users = array(); 
            while($stmt->fetch()){ 
                $user = array(); 
                $user['id'] = $id; 
                $user['email']=$email; 
                $user['name'] = $name; 
                $user['school'] = $school; 
                array_push($users, $user);
            }             
            return $users; 
        }

        public function getCours($niveau){
            $stmt = $this->con->prepare("SELECT nom, laboratoire, exercice, quiz, video, niveau, NotesDeCours FROM cours WHERE niveau = ? ;");
            $stmt->bind_param("s", $niveau);
            $stmt->execute(); 
            $stmt->bind_result($nom, $laboratoire, $exercice, $quiz , $video, $niveau, $NotesDeCours);
            $Cours = array(); 
            while($stmt->fetch()){ 
               
                $Cours['nom'] = $nom; 
                $Cours['laboratoire']= $laboratoire; 
                $Cours['exercice'] = $exercice; 
                $Cours['quiz'] = $quiz; 
                $Cours['video']= $video; 
                $Cours['niveau'] = $niveau; 
                $Cours['NotesDeCours'] = $NotesDeCours; 
               
            }             
            return $Cours; 
        }

        public function getUserByEmail($email){
            $stmt = $this->con->prepare("SELECT id, email, name, school FROM users WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute(); 
            $stmt->bind_result($id, $email, $name, $school);
            $stmt->fetch(); 
            $user = array(); 
            $user['id'] = $id; 
            $user['email']=$email; 
            $user['name'] = $name; 
            $user['school'] = $school; 
            return $user; 
        }

        public function getUserByUsername($usr){
            $stmt = $this->con->prepare("SELECT idEtudiant, changedpw FROM students WHERE username = ?");
            $stmt->bind_param("s", $usr);
            $stmt->execute(); 
            $stmt->bind_result($idEtudiant,$changedpw);
            $stmt->fetch(); 
            $user = array(); 
            $user['idEtudiant'] = $idEtudiant; 
            $user['changedpw']= $changedpw; 
         
            return $user; 
        }

        public function updateUser($id, $username, $password){
            $stmt = $this->con->prepare("UPDATE students SET username = ?, password = ? , changedpw = ? WHERE idEtudiant = ?");
            $hash_password = password_hash($password, PASSWORD_DEFAULT);
            $status = 1;
            $stmt->bind_param("ssii", $username, $hash_password, $status, $id);
            if($stmt->execute())
                return true; 
            return false; 
        }

        public function updatePassword($currentpassword, $newpassword, $email){
            $hashed_password = $this->getUsersPasswordByUsername($email);
            
            if(password_verify($currentpassword, $hashed_password)){
                
                $hash_password = password_hash($newpassword, PASSWORD_DEFAULT);
                $stmt = $this->con->prepare("UPDATE users SET password = ? WHERE email = ?");
                $stmt->bind_param("ss",$hash_password, $email);

                if($stmt->execute())
                    return PASSWORD_CHANGED;
                return PASSWORD_NOT_CHANGED;

            }else{
                return PASSWORD_DO_NOT_MATCH; 
            }
        }

        public function deleteUser($id){
            $stmt = $this->con->prepare("DELETE FROM users WHERE id = ?");
            $stmt->bind_param("i", $id);
            if($stmt->execute())
                return true; 
            return false; 
        }

        private function isEmailExist($email){
            $stmt = $this->con->prepare("SELECT idEtudiant FROM students WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute(); 
            $stmt->store_result(); 
            return $stmt->num_rows > 0;  
        }


        private function usernameExists($usr){
            $stmt = $this->con->prepare("SELECT idEtudiant FROM students WHERE username = ?");
            $stmt->bind_param("s", $usr);
            $stmt->execute(); 
            $stmt->store_result(); 
            return $stmt->num_rows > 0;  
        }
    }