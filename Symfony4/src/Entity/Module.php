<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * Module
 *
 * @ORM\Table(name="module")
 * @ORM\Entity
 * @ORM\Entity(repositoryClass="App\Repository\ModulesRepository")

 */
class Module
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * @Groups("ressource")
     *
     */
    private $id;

    /**
     * @var string
     * @Assert\NotBlank(message="No file chosen. Please choose File.")
     * @ORM\Column(name="name", type="string", length=255, nullable=false)
     * @Groups("ressource")
     */
    private $name;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }

    public function __toString()
    {
        return $this->getName();
    }


}
