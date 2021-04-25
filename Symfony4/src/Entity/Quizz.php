<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

//* @ORM\Table(name="quizz", indexes={@ORM\Index(name="fk_quizz_user_id", columns={"helper_id"})})

/**
 * Quizz
 *
 * @ORM\Entity(repositoryClass="App\Repository\QuizzRepository")
 */
class Quizz
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
     * @var string
     * @Assert\NotBlank
     *
     * @ORM\Column(name="title", type="string", length=500, nullable=false)
     */
    private $title;

    /**
     * @var string
     * @Assert\NotBlank
     *
     * @ORM\Column(name="subject", type="string", length=500, nullable=false)
     */
    private $subject;

    /**
     * @ORM\ManyToOne(targetEntity=Users::class, inversedBy="quizzs")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="helper_id", referencedColumnName="Id")
     * })
     */
    private $helper;

    /**
     * @ORM\OneToMany(targetEntity=Question::class, mappedBy="quizz", cascade={"persist", "remove"})
     */
    private $questions;

    /**
     * @ORM\OneToMany(targetEntity=QuizzResult::class, mappedBy="quizz", cascade={"persist", "remove"})
     */
    private $quizzResults;

    public function __construct()
    {
        $this->questions = new ArrayCollection();
        $this->quizzResults = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

        return $this;
    }

    public function getSubject(): ?string
    {
        return $this->subject;
    }

    public function setSubject(string $subject): self
    {
        $this->subject = $subject;

        return $this;
    }

    public function getHelper(): ?Users
    {
        return $this->helper;
    }

    public function setHelper(?Users $helper): self
    {
        $this->helper = $helper;

        return $this;
    }

    /**
     * @return Collection|Question[]
     */
    public function getQuestions(): Collection
    {
        return $this->questions;
    }

    public function addQuestion(Question $question): self
    {
        if (!$this->questions->contains($question)) {
            $this->questions[] = $question;
            $question->setQuizz($this);
        }

        return $this;
    }

    public function removeQuestion(Question $question): self
    {
        if ($this->questions->removeElement($question)) {
            // set the owning side to null (unless already changed)
            if ($question->getQuizz() === $this) {
                $question->setQuizz(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|QuizzResult[]
     */
    public function getQuizzResults(): Collection
    {
        return $this->quizzResults;
    }

    public function addQuizzResult(QuizzResult $quizzResult): self
    {
        if (!$this->quizzResults->contains($quizzResult)) {
            $this->quizzResults[] = $quizzResult;
            $quizzResult->setQuizz($this);
        }

        return $this;
    }

    public function removeQuizzResult(QuizzResult $quizzResult): self
    {
        if ($this->quizzResults->removeElement($quizzResult)) {
            // set the owning side to null (unless already changed)
            if ($quizzResult->getQuizz() === $this) {
                $quizzResult->setQuizz(null);
            }
        }

        return $this;
    }


}
