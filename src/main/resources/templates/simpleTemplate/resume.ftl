<!DOCTYPE html>
<html lang="en">
<head>
    <title>${user.firstName} ${user.lastName}'s Resume</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<article class="resume-wrapper text-center position-relative">
    <div class="resume-wrapper-inner mx-auto text-left bg-white shadow-lg">
        <header class="resume-header pt-4 pt-md-0">
            <div class="media flex-column flex-md-row">
                <img src="image/${user.id}" alt="Profile Image" border="0" width="220" height="220">
                <div class="media-body p-4 d-flex flex-column flex-md-row mx-auto mx-lg-0">
                    <div class="primary-info">
                        <h1 class="name mt-0 mb-1 text-white text-uppercase">${user.firstName} ${user.lastName}</h1>
                        <div class="title mb-3">${user.profession}</div>
                        <ul class="list-unstyled">
                            <li class="mb-2"><a href="mailto:${user.email}"><i class="far fa-envelope fa-fw mr-2" data-fa-transform="grow-3"></i>${user.email}</a></li>
                            <li><a><i class="fas fa-mobile-alt fa-fw mr-2" data-fa-transform="grow-6"></i>${user.mobile}</a></li>
                        </ul>
                    </div>
                    <div class="secondary-info ml-md-auto mt-2">
                        <ul class="resume-social list-unstyled">
                            <#list resume.networks as network>
                                <li class="mb-3"><a href="${network.link}"><span class="fa-container text-center mr-2">${network.referenceName}</span>${network.option!""}</a></li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>
        </header>

        <!-- Rest of the resume sections go here -->
        <section class="resume-section education-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Education</h2>
            <div class="resume-section-content">
                <#list resume.educations as education>
                    <div class="resume-timeline-item mb-4">
                        <div class="resume-timeline-item-header">
                            <div class="resume-timeline-item-desc">
                                <h3 class="resume-timeline-item-title text-dark font-weight-bold">${education.degree} in ${education.field}</h3>
                                <p class="font-weight-bold">${education.school}</p>
                            </div>
                            <div class="resume-timeline-item-duration">
                                ${education.startDate.toString()} - ${education.endDate.toString()}
                            </div>
                        </div>
                        <div class="resume-timeline-item-desc">
                            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                                In reprehenderit quidem maxime natus,
                                eum illum explicabo neque magnam mollitia asperiores
                                possimus autem perferendis amet quas laborum debitis officiis,
                                dolorum dicta.
                            </p>
                            <p>${education.summary}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </section>

        <section class="resume-section experience-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Work Experience</h2>
            <div class="resume-section-content">
                <#list resume.workExperiences as experience>
                    <div class="resume-timeline-item mb-4">
                        <div class="resume-timeline-item-header">
                            <div class="resume-timeline-item-desc">
                                <h3 class="resume-timeline-item-title text-dark font-weight-bold">${experience.occupiedPosition}</h3>
                                <p class="font-weight-bold">${experience.company}, ${experience.companyCity}</p>
                            </div>
                            <div class="resume-timeline-item-duration">
                                ${experience.startDate.toString()} - ${experience.endDate.toString()}
                            </div>
                        </div>
                        <div class="resume-timeline-item-desc">
                            <p>${experience.summary}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </section>

        <section class="resume-section skills-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Technical Skills</h2>
            <div class="resume-section-content">
                <#list resume.technicalExperiences as technicalExperience>
                    <div class="resume-skill-item mb-3">
                        <h3 class="resume-skill-name text-dark font-weight-bold">${technicalExperience.topic}</h3>
                        <ul class="list-inline">
                            <#list technicalExperience.details as skill>
                                <li class="list-inline-item">
                                    <span class="badge badge-primary">${skill.name} - ${skill.level}/5</span>
                                </li>
                            </#list>
                        </ul>
                    </div>
                </#list>
            </div>
        </section>

        <!-- Additional sections like tools, methodologies, skills, hobbies, trainings, etc., can be added similarly -->

        <section class="resume-section skills-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Skills</h2>
            <div class="resume-section-content">
                <div class="resume-skill-item">
                    <h4 class="resume-skills-cat font-weight-bold">Technical Skills</h4>
                    <ul class="list-unstyled mb-4">
                        <#list resume.skills as skill>
                            <li class="mb-2">
                                <div class="resume-skill-name">${skill}</div>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </section>

        <section class="resume-section skills-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Tools</h2>
            <div class="resume-section-content">
                <div class="resume-skill-item">
                    <h4 class="resume-skills-cat font-weight-bold">Proficient in</h4>
                    <ul class="list-unstyled mb-4">
                        <#list resume.tools as tool>
                            <li class="mb-2">
                                <div class="resume-skill-name">${tool}</div>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </section>

        <section class="resume-section skills-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Methodologies</h2>
            <div class="resume-section-content">
                <div class="resume-skill-item">
                    <h4 class="resume-skills-cat font-weight-bold">Experience with</h4>
                    <ul class="list-unstyled mb-4">
                        <#list resume.methodologies as methodology>
                            <li class="mb-2">
                                <div class="resume-skill-name">${methodology}</div>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </section>

        <section class="resume-section interests-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Hobbies and Interests</h2>
            <div class="resume-section-content">
                <ul class="list-unstyled">
                    <#list resume.hobbiesAndInterest as hobby>
                        <li class="mb-1">${hobby}</li>
                    </#list>
                </ul>
            </div>
        </section>

        <section class="resume-section education-section mb-5">
            <h2 class="resume-section-title text-uppercase font-weight-bold pb-3 mb-3">Trainings and Certifications</h2>
            <div class="resume-section-content">
                <ul class="list-unstyled">
                    <#list resume.trainingsAndCertifications as training>
                        <li class="mb-2">
                            <div class="resume-degree font-weight-bold">${training}</div>
                        </li>
                    </#list>
                </ul>
            </div>
        </section>


    </div>
</article>
</body>
</html>
