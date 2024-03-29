<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * UpvoteComment
 *
 * @ORM\Table(name="upvote_comment", indexes={@ORM\Index(name="fk_idcomment", columns={"id_comment"}), @ORM\Index(name="fk_userid", columns={"id_user"})})
 * @ORM\Entity
 */
class UpvoteComment
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    /**
     * @var \Comment
     *
     * @ORM\ManyToOne(targetEntity="Comment")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_comment", referencedColumnName="id")
     * })
     */
    private $idComment;

    /**
     * @var \Users
     *
     * @ORM\ManyToOne(targetEntity="Users")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="Id")
     * })
     */
    private $idUser;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdComment(): ?Comment
    {
        return $this->idComment;
    }

    public function setIdComment(?Comment $idComment): self
    {
        $this->idComment = $idComment;

        return $this;
    }

    public function getIdUser(): ?Users
    {
        return $this->idUser;
    }

    public function setIdUser(?Users $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }


}
