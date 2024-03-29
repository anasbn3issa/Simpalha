<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210413115327 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE candidatures CHANGE idc idc INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE comment CHANGE owner_id owner_id INT DEFAULT NULL, CHANGE rating rating INT NOT NULL, CHANGE upvotes upvotes INT NOT NULL, CHANGE downvotes downvotes INT NOT NULL, CHANGE id_Post id_Post INT DEFAULT NULL');
        $this->addSql('ALTER TABLE disponibilite CHANGE helperId helperId INT DEFAULT NULL, CHANGE etat etat INT NOT NULL');
        $this->addSql('ALTER TABLE downvote_comment CHANGE id_user id_user INT DEFAULT NULL, CHANGE id_comment id_comment INT DEFAULT NULL');
        $this->addSql('ALTER TABLE feedback CHANGE id_meet id_meet VARCHAR(60) DEFAULT NULL');
        $this->addSql('ALTER TABLE invitation CHANGE idv idv INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE meet CHANGE id id VARCHAR(60) NOT NULL, CHANGE id_student id_student INT DEFAULT NULL, CHANGE id_helper id_helper INT DEFAULT NULL, CHANGE disponibilite_id disponibilite_id INT DEFAULT NULL, CHANGE etat etat INT NOT NULL');
        $this->addSql('ALTER TABLE post ADD title VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE question DROP FOREIGN KEY fk_quizz_id');
        $this->addSql('ALTER TABLE question CHANGE quizz_id quizz_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE question ADD CONSTRAINT FK_B6F7494EBA934BCD FOREIGN KEY (quizz_id) REFERENCES quizz (id)');
        $this->addSql('ALTER TABLE reclamation CHANGE StudentId StudentId INT DEFAULT NULL');
        $this->addSql('ALTER TABLE upvote_comment CHANGE id_user id_user INT DEFAULT NULL, CHANGE id_comment id_comment INT DEFAULT NULL');
        $this->addSql('ALTER TABLE users DROP Spécialité, CHANGE role role INT NOT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE candidatures CHANGE idc idc INT NOT NULL');
        $this->addSql('ALTER TABLE comment CHANGE owner_id owner_id INT NOT NULL, CHANGE rating rating INT DEFAULT 0 NOT NULL, CHANGE upvotes upvotes INT DEFAULT 0 NOT NULL, CHANGE downvotes downvotes INT DEFAULT 0 NOT NULL, CHANGE id_Post id_Post INT NOT NULL');
        $this->addSql('ALTER TABLE disponibilite CHANGE etat etat INT DEFAULT 0 NOT NULL, CHANGE helperId helperId INT NOT NULL');
        $this->addSql('ALTER TABLE downvote_comment CHANGE id_comment id_comment INT NOT NULL, CHANGE id_user id_user INT NOT NULL');
        $this->addSql('ALTER TABLE feedback CHANGE id_meet id_meet VARCHAR(60) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('ALTER TABLE invitation CHANGE idv idv INT NOT NULL');
        $this->addSql('ALTER TABLE meet CHANGE id id VARCHAR(60) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE disponibilite_id disponibilite_id INT NOT NULL, CHANGE id_helper id_helper INT NOT NULL, CHANGE id_student id_student INT NOT NULL, CHANGE etat etat INT DEFAULT 0 NOT NULL');
        $this->addSql('ALTER TABLE post DROP title');
        $this->addSql('ALTER TABLE question DROP FOREIGN KEY FK_B6F7494EBA934BCD');
        $this->addSql('ALTER TABLE question CHANGE quizz_id quizz_id INT NOT NULL');
        $this->addSql('ALTER TABLE question ADD CONSTRAINT fk_quizz_id FOREIGN KEY (quizz_id) REFERENCES quizz (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE reclamation CHANGE StudentId StudentId INT NOT NULL');
        $this->addSql('ALTER TABLE upvote_comment CHANGE id_comment id_comment INT NOT NULL, CHANGE id_user id_user INT NOT NULL');
        $this->addSql('ALTER TABLE users ADD Spécialité VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, CHANGE role role INT DEFAULT 0 NOT NULL');
    }
}
