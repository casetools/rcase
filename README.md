RC-ASE: Requirements for Context-Aware Systems Engineering  
[![Build Status](https://img.shields.io/badge/casetools-rcase-blue.svg)](https://travis-ci.org/joemccann/dillinger) [![Build Status](https://img.shields.io/badge/version-0.4.8-green.svg)](https://travis-ci.org/joemccann/dillinger)  
======

[Modelio](https://www.modelio.org/) is an open-source modelling project. The project in this repository contains the code and [binaries](https://github.com/ualegre/rcase/tree/master/rcase/target) of a Modelio module 
which has been created in order to aid developers of context-aware systems during the requirements elicitation development stage. It is complementary to another Modelio module for supporting the design and code generation of context-aware systems [DC-ASE](https://github.com/ualegre/dcase). It is recommended to be installed and used together with (at least) the open-source version of the [SysML Architect Modelio Module](http://store.modelio.org/resource/modules/sysml-architect-open-source.html).  

The module includes the following features:
* Stakeholder Diagram
* Objective Diagram	
* Requirements Diagram (SysML)
* Situation of Interest Diagram
* Situation Detection Plan Diagram
* Requirements traceability matrixes (SysML)
* Requirements tables (SysML)
* Automated documentation generation (spreadsheets)

More information on the theoretical aspects of this module can be found in:  
1. *Engineering context-aware systems and applications: A survey, Journal of Systems and Software, Volume 117, Pages 55-83, Elsevier.*  
**Download here:** 
	* [Mirror 1](https://doi.org/10.1016/j.jss.2016.02.010) 
	* [Mirror 2](http://eprints.mdx.ac.uk/18845/)
2. *Perspectives on engineering more usable context-aware systems, Journal of Ambient Intelligence and Humanized Computing, Volume 9, Number 5, Pages 1593-1609, Springer.*  
**Download here:** 
	* [Mirror 1](https://doi.org/10.1007/s12652-018-0863-7) 
	* [Mirror 2](http://eprints.mdx.ac.uk/24280/)
3. *RC-ASEF: An open-source tool-supported requirements elicitation framework for context-aware systems development, Proceedings of the 2018 Federated Conference on Computer Science and Information Systems, FedCSIS, Poland, 2018, Pages 829-838, IEEE.*   
**Download here:** 
	* [Mirror 1](https://doi.org/10.15439/2018F136)  

## How-to use the content of this repository
### Instructions for users
Modelio is an open-source modelling tool which is freely available to be [downloaded](https://www.modelio.org/downloads/download-modelio.html) from its [official website](http://www.modelio.org). The current Modelio version for which this module is compatible is v3.7. Modelio, and therefore this module, is available for Linux, Windows and Mac. Follow the [Modelio Quick-start guide](https://www.modelio.org/quick-start-pages-35.html) provided in the official Modelio website to install the program in your preferred operating system. 

In order to install the RC-ASE Modelio module, follow the [official guide for working with modules](https://www.modelio.org/quick-start-pages-35/928-modelio/quick-start-35x/132-working-with-modules-35.html). You can download the latest RC-ASE Modelio module (.jmdac) directly from the [rcase/target](https://github.com/ualegre/rcase/blob/master/rcase/target/) folder of the repository. Other versions of the binaries can be directly downloaded below: 

* [RC-ASE v0.4.8](https://github.com/ualegre/rcase/raw/master/rcase/target/RCase_0.4.8.jmdac)

### Instructions for developers
For developing this module, it is recommended to have the Eclipse RCP neon with Maven (M2e) and Git (EGit) plugins correctly installed. Then, follow the steps:
1. Clone this repository to your local hard disk.
2. Open an Eclipse RCP neon instance in your preferred workspace (different than the folder where you have downloaded your repository)
3. Click on Import -> Existing Maven Projects -> (Select as root directory the folder where you have cloned your repo). EGit should automatically detect 
that you are using a repository.
4. To produce a new version of the .jmdac Modelio module file: Left click on the folder -> Run as -> 7 Maven install. 

## License 
This project is licensed under the [GNU General Public License v3.0](https://github.com/casetools/rcase/blob/master/LICENSE.md).

### Third party libraries 
The project uses the following third party libraries:
* [poi-3.14-20160307.jar](https://poi.apache.org), [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
* [poi-ooxml-3.14-20160307.jar](https://poi.apache.org), [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
* [poi-ooxml-schemas-3.14-20160307.jar](https://poi.apache.org), [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
* [xml-apis-2.0.2.jar](https://xerces.apache.org/xml-commons/), [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
* [xmlbeans-2.6.0.jar](https://xmlbeans.apache.org/), [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

### Third party icons
The stereotypes of the UML profile introduced as part of the new diagrams available include different icons, which have been made and released for free use by the following users of [Flaticon](https://www.flaticon.com/): 
* [Freepik](https://www.flaticon.com/authors/freepik)
* [GraphicsBay](http://www.flaticon.com/authors/graphicsbay)
* [Pixel Perfect](https://www.flaticon.com/authors/pixel-perfect)
* [Dave Gandy](http://www.flaticon.com/authors/dave-gandy)
* [Gregor Cresnar](https://www.flaticon.com/authors/gregor-cresnar)
* [Hand Drawn Goods](https://www.flaticon.com/authors/hand-drawn-goods).

## Developer Contact
This work has been created as part of the doctoral thesis contribution of Unai Alegre-Ibarra. The repository is open to pull-requests. 
* author: Unai Alegre-Ibarra
* e-mail: u.alegre@mdx.ac.uk
