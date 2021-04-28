<?php

namespace App\Entity;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * Ressources
 *
 * @ORM\Table(name="ressources")
 * @ORM\Entity
 * @ORM\Entity(repositoryClass="App\Repository\RessourcesRepository")
 */
class Ressources
{
    /**
     * @var int
     *
     * @ORM\Column(name="idR", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idr;

    /**
     * @var string
     * @Assert\NotBlank(message="No file chosen. Please choose File.")
     * @ORM\Column(name="path", type="string", length=255, nullable=false)
     */
    private $path;

    /**
     * @var string
     * @Assert\NotBlank(message="Title Cannot be accepted : Blank")
     * @ORM\Column(name="title", type="string", length=255, nullable=false)
     */
    private $title;

    /**
     * @var string
     * @Assert\NotBlank(message="Description Cannot be accepted : Blank")
     * @Assert\Length(min="10",
     *      minMessage=" Minimum description's length = {{ limit }}")
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     */
    private $description;

    /**
     * @var string
     * @Assert\NotBlank (message="Module Cannot be accepted : Blank")
     * @ORM\Column(name="module", type="string", length=255, nullable=false)
     */
    private $module;

    protected $captchaCode;

    public function getIdr(): ?int
    {
        return $this->idr;
    }

    public function getPath()
    {
        return $this->path;
    }

    public function setPath($path): self
    {
        $this->path = $path;

        return $this;
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

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getModule(): ?string
    {
        return $this->module;
    }

    public function setModule(string $module): self
    {
        $this->module = $module;

        return $this;
    }
    public function getCaptchaCode()
    {
        return $this->captchaCode;
    }

    public function setCaptchaCode($captchaCode)
    {
        $this->captchaCode = $captchaCode;

        return $this;
    }



}
