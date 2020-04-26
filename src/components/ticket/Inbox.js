import React from 'react';
import {
  Button,
  Row,
  Col,
  Badge,
  CardBody,
  Card,
  Dropdown,
  DropdownMenu,
  DropdownItem,
  DropdownToggle,
} from 'reactstrap';

const messages = [
  {
    id: 1,
    from: 'Gary Lewis',
    fromAddress: 'test@foofoo.com',
    subject: 'I saw this posting on the board',
    dtSent: 'Today, 9:18AM',
    read: false,
    body:
      'Hey Mark,<br><br>I saw your post on the message board and I was wondering if you still had that item available. Can you call me if you still do?<br><br>Thanks,<br><b>Gary Lewis</b>',
  },
  {
    id: 2,
    from: 'Bob Sutton',
    fromAddress: 'test@testdomain.com',
    subject: 'In Late Today',
    dtSent: 'Today, 8:54AM',
    read: false,
    body: 'Mark,<br>I will be in late today due to an appt.<br>v/r Bob',
    attachment: false,
  },
  {
    id: 3,
    from: 'Will Adivo',
    fromAddress: 'test@testbar.com',
    subject: 'New developer',
    dtSent: 'Yesterday, 4:48PM',
    read: true,
    body:
      'Here is the last resume for the developer position we posted on SO. Please review and let me know your thoughts!',
    attachment: true,
  },
  {
    id: 4,
    from: 'Al Kowalski',
    fromAddress: 'test@domain.com',
    subject: 'RE: New developer',
    dtSent: 'Yesterday, 4:40PM',
    read: false,
    body: 'I looked at the resume, but the guy looks like a moron.',
    priority: 1,
  },
  {
    id: 4,
    from: 'Beth Maloney',
    fromAddress: 'test@mail.com',
    subject: 'July Reports',
    dtSent: '3 Days Ago',
    read: true,
    body:
      "PYC Staff-<br> Our weekly meeting is canceled due to the holiday. Please review and submit your PID report before next week's meeting.<br>Thanks,<br>Beth",
  },
  {
    id: 6,
    from: 'Jason Furgano',
    fromAddress: 'test@domain.com',
    subject: 'New developer',
    dtSent: '3 Days Ago',
    read: true,
    body:
      "All,<br>I'd like to introduce Joe Canfigliata our new S/W developer. If you see him in the office introduce yourself and make him feel welcome.",
  },
  {
    id: 7,
    from: 'Bob Sutton',
    fromAddress: 'test@test.com',
    subject: 'Tasking request',
    dtSent: '3 Days Ago',
    read: true,
    body: 'Ovi lipsu doir. The message body goes here...',
  },
  {
    id: 8,
    from: 'Will Adivo',
    fromAddress: 'test@test.com',
    subject: 'Proposal for Avid Consulting',
    dtSent: '3 Days Ago',
    read: true,
    body:
      'Mark, I reviewed your proposal with Beth and we have a few questions. Let me know when you time to meet.',
  },
  {
    id: 9,
    from: 'Philip Corrigan',
    fromAddress: 'test@testdomain.com',
    subject: 'Follow-up Appt.',
    dtSent: '4 Days Ago',
    read: true,
    body:
      'Hi,<br>Can you please confirm the expense report I submitted for my last trip to SD?<br>Thanks,<br>Tom Grey',
  },
];

class ModalMessage extends React.Component {
  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = {
      modalClasses: ['modal', 'fade'],
    };
  }

  toggle() {
    console.log(this.refs.messageModal);
    document.body.className += ' modal-open';

    let modalClasses = this.state.modalClasses;

    if (modalClasses.indexOf('show') > -1) {
      modalClasses.pop();
      let backdrop = document.querySelector('.modal-backdrop');
      document.body.removeChild(backdrop);
    } else {
      modalClasses.push('show');
      let backdrop = document.createElement('div');
      backdrop.classList = 'modal-backdrop fade show';
      document.body.appendChild(backdrop);
    }

    this.setState({
      modalClasses,
    });
  }

  render() {
    console.log('message', this.props.message);
    return (
      <div
        id='messageModal'
        className={this.state.modalClasses.join(' ')}
        tabIndex='-1'
        role='dialog'
        aria-hidden='true'
        ref='messageModal'
      >
        <div className='modal-dialog modal-dialog-centered modal-lg'>
          <div className='modal-content'>
            <div className='modal-header'>
              <div>
                <small className='text-uppercase text-muted'>Subject</small>
                <h4 className='modal-title'>{this.props.message.subject}</h4>
              </div>
              <button
                type='button'
                className='close'
                data-dismiss='modal'
                aria-label='Close'
                onClick={this.toggle}
              >
                <span aria-hidden='true'>×</span>
                <span className='sr-only'>Close</span>
              </button>
            </div>
            <div className='modal-body'>
              <div className='row'>
                <div className='col-sm-8'>
                  <small className='text-uppercase text-muted'>From</small>
                  <h4>
                    <a href="'mailto:'+selected.fromAddress">
                      {this.props.message.from}
                    </a>
                  </h4>
                </div>
                <div className='col-sm-4'>
                  <small className='text-uppercase text-muted'>Sent</small>
                  <h6>{this.props.message.dtSent}</h6>
                </div>
                <div className='col-sm-12'>
                  <p
                    dangerouslySetInnerHTML={{
                      __html: this.props.message.body,
                    }}
                  />
                </div>
              </div>
              <p className='my-3' />
              <button
                className='btn btn-primary float-right ml-2'
                data-dismiss='modal'
                aria-hidden='true'
                onClick={this.toggle}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

class Inbox extends React.Component {
  constructor(props) {
    super(props);
    this.doShow = this.doShow.bind(this);
    this.doDelete = this.doDelete.bind(this);
    this.toggleMark = this.toggleMark.bind(this);
    this.toggleMarkAll = this.toggleMarkAll.bind(this);
    this.deleteMarked = this.deleteMarked.bind(this);
    this.refreshMessages = this.refreshMessages.bind(this);
    this.deleteMessages = this.deleteMessages.bind(this);
    this.toogleDropdownOpen = this.toogleDropdownOpen.bind(this);
    this.ModalMessage = React.createRef();
    this.ModalCompose = React.createRef();
    this.state = {
      initMessages: messages,
      messages: messages,
      selected: {},
      dropdownOpened: false,
    };
  }

  toogleDropdownOpen() {
    let opened = this.state.dropdownOpened;
    this.setState({ dropdownOpened: !opened });
  }

  doShow(idx) {
    console.log(this.state.messages[idx]);
    this.setState({
      selected: messages[idx],
    });
    /* open message in modal */
    this.ModalMessage.current.toggle();
  }

  toggleMark(idx) {
    let messages = [...this.state.messages];
    messages[idx].marked = messages[idx].marked ? 0 : 1;
    this.setState({ messages });
  }

  doDelete(idx) {
    let messages = [...this.state.messages];
    /* append it to deleted */
    /* remove the message at idx */
    messages.splice(idx, 1);
    this.setState({ messages });
  }

  toggleMarkAll() {
    let messages = [...this.state.messages];
    messages.map((v, k) => {
      return (v.marked = v.marked ? 0 : 1);
    });
    this.setState({ messages });
  }

  deleteMarked() {
    var self = this;
    let messages = [...this.state.messages];
    var tbd = [];
    for (var k = 0; k < messages.length; k++) {
      if (messages[k].marked === 1) {
        tbd.push(k);
      }
    }

    if (tbd.length > 0) {
      self.deleteMessages(tbd);
    }
  }

  refreshMessages() {
    let initMessages = [...this.state.initMessages];
    this.setState({ messages: initMessages });
  }

  deleteMessages(arr) {
    let messages = [...this.state.messages];
    for (var i = arr.length - 1; i >= 0; i--) {
      messages.splice(arr[i], 1);
    }
    this.setState({ messages });
  }

  render() {
    return (
      <Row>
        <Col>
          <Card>
            <CardBody>
              <Row>
                <Col lg='3' md='4' className='py-3'>
                  <ul className='list-group sticky-top sticky-offset'>
                    <div className='nav nav-pills py-2 flex-md-column justify-content-center'>
                      <a
                        href={{ void: 0 }}
                        className='nav-link active'
                        title='Messages'
                        data-toggle='tab'
                        data-target='#messages'
                      >
                        <span className='icon icon-envelope fa fa-fw fa-envelope mr-md-1' />
                        <span className='d-none d-md-inline'>Yêu cầu</span>
                        <Badge className='ml-1' color='danger'>
                          {
                            this.state.messages.filter((v, k) => {
                              return !v.read;
                            }).length
                          }
                        </Badge>
                      </a>
                      <a
                        href='#deleted'
                        className='nav-link'
                        title='Deleted'
                        data-toggle='tab'
                        data-target='#deleted'
                      >
                        <span className='icon icon-trash fa fa-fw fa-trash mr-md-1' />
                        <span className='d-none d-md-inline'>
                          Đã giải quyết
                        </span>
                      </a>
                    </div>
                  </ul>
                </Col>

                <Col lg='9' md='8' className='py-3'>
                  <div id='messages' className='tab-pane active'>
                    <div className='d-flex flex-sm-row flex-column py-1 mb-1'>
                      <div className='btn-group'>
                        <button
                          type='button'
                          className='btn btn-outline text-uppercase'
                          onClick={this.toggleMarkAll}
                        >
                          <div
                            className='custom-control custom-checkbox'
                            onClick={this.toggleMarkAll}
                          >
                            <input
                              type='checkbox'
                              className='custom-control-input'
                              id='checkAll'
                              defaultChecked={false}
                              onChange={this.toggleMarkAll}
                            />
                            <label
                              className='custom-control-label'
                              htmlFor='checkAll'
                            >
                              Mark
                            </label>
                          </div>
                        </button>
                        {this.state.messages &&
                        this.state.messages.filter((v, k) => {
                          if (v.marked === 1) {
                            return v;
                          }
                        }).length > 0 ? (
                          <Dropdown
                            isOpen={this.state.dropdownOpened}
                            toggle={() => {
                              this.toogleDropdownOpen();
                            }}
                          >
                            <DropdownToggle caret></DropdownToggle>
                            <DropdownMenu>
                              <DropdownItem onClick={this.deleteMarked}>
                                Delete marked items
                              </DropdownItem>
                            </DropdownMenu>
                          </Dropdown>
                        ) : null}
                      </div>
                      <Button
                        outline
                        color='primary'
                        onClick={this.refreshMessages}
                      >
                        <i className='align-middle fa fa-refresh' />
                      </Button>
                    </div>
                    {/* message list */}
                    <ul className='list-group py-2'>
                      {this.state.messages && this.state.messages.length > 0
                        ? this.state.messages.map((item, idx) => (
                            <li
                              key={idx}
                              className='list-group-item list-group-item-action d-block py-1'
                            >
                              <summary className='row'>
                                <div className='col py-2 order-1'>
                                  <div onClick={() => this.doShow(idx)}>
                                    <a>
                                      {item.from}{' '}
                                      <span className='icon fa fa-fw fa-envelope mr-md-1' />
                                    </a>
                                  </div>
                                </div>
                                <div className='col-auto px-0 order-last order-sm-2 d-none d-sm-block align-self-center text-right'>
                                  <Button
                                    outline
                                    title='Deleted'
                                    color='success'
                                    onClick={() => this.doDelete(idx)}
                                    className='mr-3'
                                  >
                                    <span className='cui-check icons font-xl d-block' />
                                  </Button>
                                  <Button
                                    outline
                                    title='Deleted'
                                    color='danger'
                                    onClick={() => this.doDelete(idx)}
                                  >
                                    <span className='cui-trash icons font-xl d-block' />
                                  </Button>
                                </div>
                                <div
                                  className='col-sm-12 col-10 py-2 order-3'
                                  onClick={() => this.doShow(idx)}
                                >
                                  <div className='float-right text-right'>
                                    <span className={' d-none d-sm-block '}>
                                      {item.dtSent}
                                    </span>
                                  </div>
                                  <p className='lead mb-0'>
                                    <a href='javascript:void(0)'>
                                      {item.subject}
                                    </a>
                                  </p>
                                </div>
                              </summary>
                            </li>
                          ))
                        : null}
                    </ul>
                  </div>
                </Col>
              </Row>
            </CardBody>
          </Card>
          <ModalMessage ref={this.ModalMessage} message={this.state.selected} />
        </Col>
      </Row>
    );
  }
}

export default Inbox;
